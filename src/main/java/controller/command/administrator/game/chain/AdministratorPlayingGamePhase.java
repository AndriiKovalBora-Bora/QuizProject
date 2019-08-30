/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.hint.Hint;
import model.entities.hint.LocalizedHint;
import model.entities.question.LocalizedQuestion;
import model.entities.question.Question;
import model.entities.statistics.Statistics;
import model.entities.user.LocalizedUser;
import model.entities.user.User;
import model.entities.hint.TypeOfHint;
import model.entities.question.TypeOfQuestion;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes AdministratorPlayingGamePhase
 */
public class AdministratorPlayingGamePhase extends AdministratorGamePhase implements Command {

    /**
     * Question service
     */
    private QuestionService questionService;

    /**
     * User service
     */
    private UserService userService;

    /**
     * Time service
     */
    private TimeService timeService;

    /**
     * Hint service
     */
    private HintService hintService;

    /**
     * Statistics service
     */
    private StatisticsService statisticsService;

    /**
     * Types of question for displaying correct page for question
     */
    private Map<TypeOfQuestion, String> typeOfQuestionPage = new HashMap<>();

    /**
     * Creates AdministratorPlayingGamePhase class
     *
     * @param questionService   Question service
     * @param userService       User service
     * @param timeService       Time service
     * @param hintService       Hint service
     * @param statisticsService Statistics service
     * @param phase             The number of current phase
     */
    AdministratorPlayingGamePhase(QuestionService questionService, UserService userService, TimeService timeService, HintService hintService, StatisticsService statisticsService, int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("AdministratorPlayingGamePhase class constructor");
        this.questionService = questionService;
        this.userService = userService;
        this.timeService = timeService;
        this.hintService = hintService;
        this.statisticsService = statisticsService;
        typeOfQuestionPage.put(TypeOfQuestion.WITHOUT_CHOICES, "/WEB-INF/question.type/questionWithoutChoicesAdministrator.jsp");
        typeOfQuestionPage.put(TypeOfQuestion.WITH_CHOICES, "/WEB-INF/question.type/questionWithChoicesAdministrator.jsp");
    }

    /**
     * Executes AdministratorPlayingGamePhase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("AdministratorPlayingGamePhase class, execute method");
        request.getSession().setAttribute("phase", Constants.ADMINISTRATOR_GAME_PHASE);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        List<Statistics> activeGames = ((ArrayList<Statistics>) request.getSession().getServletContext().getAttribute("activeGames"));

        Optional<User> administrator = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!administrator.isPresent()) {
            Logger.getLogger(this.getClass()).error("Administrator is not present");
            return "/WEB-INF/view/error.jsp";
        }

        Optional<Statistics> activeGame = activeGames
                .stream()
                .filter(x -> x.getAdministrator().equals(administrator.get()))
                .findAny();
        if (!activeGame.isPresent()) {
            Logger.getLogger(this.getClass()).info("Game is not present");
            return "/WEB-INF/administrator/game/administratorGamePhase.jsp";
        }

        List<LocalizedQuestion> freeQuestions = questionService
                .getAllFreeQuestions()
                .stream()
                .map(x -> x.getLocaleLocalizedQuestions().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("freeQuestions", freeQuestions);

        HashMap<Integer, ArrayList<ArrayList<String>>> chats = (HashMap<Integer, ArrayList<ArrayList<String>>>) request.getSession().getServletContext().getAttribute("chats");
        List<ArrayList<String>> chat = chats.get(activeGame.get().getId());
        if ((request.getParameter("sendMessage") != null)
                && (request.getParameter("message") != null)
                && !(request.getParameter("message").equals("")))
            chat.add(new ArrayList<String>() {{
                add(0, administrator.get().getId().toString());
                add(1, request.getParameter("message"));
            }});

        List<String> localizedChat = new ArrayList<>();
        chat.forEach(x -> {
            LocalizedUser localizedUser = userService.getUserById(Integer.valueOf(x.get(0))).getLocalizedUsers().get(locale);
            localizedChat.add(localizedUser.getName() + "  " + localizedUser.getSurname() + ": " + x.get(1));
        });
        request.setAttribute("chat", localizedChat);

        Optional<Question> activeQuestion = activeGame.get().getQuestions().stream()
                .reduce((first, second) -> second);

        if ((request.getParameter("askQuestion")) != null) {
            Logger.getLogger(this.getClass()).info("Ask question");
            request.getSession().removeAttribute("assessed");
            if (request.getSession().getAttribute("oldTime") != null)
                activeGame.get().getConfiguration().setTime((int) request.getSession().getAttribute("oldTime"));

            activeQuestion = Optional.of(questionService.getQuestionById(request.getParameter("question")));
            activeQuestion.ifPresent(x -> {
                x.setStartTime(new Date().getTime());
                x.getStatistics().setId(activeGame.get().getId());
            });
            System.out.println(activeQuestion.get());
            questionService.updateQuestion(activeQuestion.get());
            request.setAttribute("typeOfQuestionPage", typeOfQuestionPage.get(activeQuestion.get().getTypeOfQuestion()));
            activeGame.get().getQuestions().add(activeQuestion.get());

            int timeLeft = timeService.calculateTimeLeft(activeQuestion.get(), activeGame.get().getConfiguration());
            request.setAttribute("time", timeLeft);
        }

        request.setAttribute("configuration", activeGame.get().getConfiguration().getLocalizedConfigurations().get(locale));
        request.setAttribute("activeGame", activeGame.get());

        if (!activeQuestion.isPresent()) {
            Logger.getLogger(this.getClass()).info("ActiveQuestion is not present");
            return "/WEB-INF/administrator/game/administratorGamePhase.jsp";
        }

        if (request.getParameter("sendHint") != null) {
            Logger.getLogger(this.getClass()).info("Send hint");
            Hint hint = hintService.getHintById(request.getParameter("hint"));
            activeQuestion.get().getHints().add(hint);
            activeGame.get().setNumberOfHints(hintService.addHintToCount(activeGame.get()));
            if (hint.getTypeOfHint().equals(TypeOfHint.ADD_TIME)) {
                request.getSession().setAttribute("oldTime", activeGame.get().getConfiguration().getTime());
                timeService.addTime(activeGame.get());
            }
            questionService.updateQuestion(activeQuestion.get());
            statisticsService.updateStatistics(activeGame.get());
        }

        if (activeQuestion.get().getPlayerAnswer() == null) {
            int timeLeft = timeService.calculateTimeLeft(activeQuestion.get(), activeGame.get().getConfiguration());
            request.setAttribute("time", timeLeft);
            response.setIntHeader("refresh", Constants.ADMINISTRATOR_REFRESH_TIME);
            if (timeLeft < 0) {
                activeQuestion.get().setEndTime(new Date().getTime());
                activeQuestion.get().setPlayerAnswer(Constants.noAnswer);
                questionService.updateQuestion(activeQuestion.get());
            }
        }

        if (activeQuestion.get().getPlayerAnswer() != null) {
            request.setAttribute("timeSpent", timeService.calculateTimeForAnswer(activeQuestion.get()));
        }

        activeQuestion.get().getHints().forEach(x -> {
            if (x.getTypeOfHint().equals(TypeOfHint.SHOW_HINT))
                request.setAttribute("showHint", Boolean.TRUE);
            if (x.getTypeOfHint().equals(TypeOfHint.SHOW_PROBABILITY))
                request.setAttribute("probabilities", hintService.calculateProbabilities());
            if (x.getTypeOfHint().equals(TypeOfHint.REMOVE_CHOICES))
                request.setAttribute("removeChoices", Boolean.TRUE);
        });

        if (request.getParameter("assessAnswer") != null) {
            Logger.getLogger(this.getClass()).info("Assess answer");
            if (request.getParameter("assessAnswer").equals("correctAnswer")) {
                statisticsService.givePointToPlayers(activeGame.get());
                request.getSession().setAttribute("assessed", Boolean.TRUE);
                statisticsService.updateStatistics(activeGame.get());
            }

            if (request.getParameter("assessAnswer").equals("incorrectAnswer")) {
                statisticsService.givePointToSpectators(activeGame.get());
                request.getSession().setAttribute("assessed", Boolean.TRUE);
                statisticsService.updateStatistics(activeGame.get());
            }

            if ((activeGame.get().getPlayersScore() == activeGame.get().getConfiguration().getMaxScore())
                    || (activeGame.get().getSpectatorsScore() == activeGame.get().getConfiguration().getMaxScore())) {
                chats.remove(activeGame.get().getId());
                Logger.getLogger(this.getClass()).info("Next phase : ADMINISTRATOR_STATISTICS_PHASE");
                return nextPhase.executePhase(request, response, Constants.ADMINISTRATOR_STATISTICS_PHASE);
            }
        }

        request.setAttribute("activeQuestion", activeQuestion.get().getLocaleLocalizedQuestions().get(locale));

        if (activeQuestion.get().getHints() != null) {
            List<LocalizedHint> questionHints = new ArrayList<>();
            activeQuestion.get().getHints()
                    .forEach(x -> questionHints.add(x.getLocalizedHints().get(locale)));
            request.setAttribute("questionHints", questionHints);
        }

        List<Hint> hints = hintService
                .getAllHintsByTypeOfQuestion(activeQuestion.get().getTypeOfQuestion());
        activeQuestion.get().getHints().forEach(hints::remove);

        List<LocalizedHint> localizedHints = hints
                .stream()
                .map(x -> x.getLocalizedHints().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("hints", localizedHints);
        request.setAttribute("typeOfQuestionPage", typeOfQuestionPage.get(activeQuestion.get().getTypeOfQuestion()));

        request.setAttribute("activeGame", activeGame.get());
        return "/WEB-INF/administrator/game/administratorGamePhase.jsp";
    }
}
