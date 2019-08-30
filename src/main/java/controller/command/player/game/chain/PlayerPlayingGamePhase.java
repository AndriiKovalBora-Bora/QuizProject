/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.hint.LocalizedHint;
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

/**
 * Processes PlayerPlayingGamePhase
 */
public class PlayerPlayingGamePhase extends PlayerGamePhase implements Command {

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
     * Question service
     */
    private QuestionService questionService;

    /**
     * Types of question for displaying correct page for question
     */
    private Map<TypeOfQuestion, String> typeOfQuestionPage = new HashMap<>();

    /**
     * Creates PlayerPlayingGamePhase class
     *
     * @param userService       User service
     * @param timeService       Time service
     * @param hintService       Hint service
     * @param statisticsService Statistics Service
     * @param questionService   Question service
     * @param phase             The number of current phase
     */
    PlayerPlayingGamePhase(UserService userService, TimeService timeService, HintService hintService, StatisticsService statisticsService, QuestionService questionService, int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("PlayerPlayingGamePhase class constructor");
        this.userService = userService;
        this.timeService = timeService;
        this.hintService = hintService;
        this.questionService = questionService;
        this.statisticsService = statisticsService;
        typeOfQuestionPage.put(TypeOfQuestion.WITHOUT_CHOICES, "/WEB-INF/question.type/questionWithoutChoicesPlayer.jsp");
        typeOfQuestionPage.put(TypeOfQuestion.WITH_CHOICES, "/WEB-INF/question.type/questionWithChoicesPlayer.jsp");
    }

    /**
     * Executes player playing game phase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("PlayerPlayingGamePhase class, execute method");
        request.getSession().setAttribute("phase", Constants.PLAYER_GAME_PHASE);
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        Optional<User> player = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!player.isPresent()) {
            Logger.getLogger(this.getClass()).error("Player is not present");
            return "/WEB-INF/view/error.jsp";
        }

        List<Statistics> activeGames = (ArrayList<Statistics>) request.getSession().getServletContext().getAttribute("activeGames");
        Optional<Statistics> activeGame = activeGames.stream().filter(x -> x.getPlayers().stream().anyMatch(y -> y.equals(player.get())))
                .findAny();

        if (!activeGame.isPresent()) {
            Logger.getLogger(this.getClass()).info("Game is not present");
            response.setIntHeader("refresh", 5);
            return "/WEB-INF/player/game/playerGamePhase.jsp";
        }

        request.setAttribute("activeGame", activeGame.get());

        HashMap<Integer, ArrayList<ArrayList<String>>> chats = (HashMap<Integer, ArrayList<ArrayList<String>>>) request.getSession().getServletContext().getAttribute("chats");
        List<ArrayList<String>> chat = chats.get(activeGame.get().getId());
        if (chat != null) {
            if ((request.getParameter("sendMessage") != null)
                    && (request.getParameter("message") != null)
                    && !(request.getParameter("message").equals("")))
                chat.add(new ArrayList<String>() {{
                    add(0, player.get().getId().toString());
                    add(1, request.getParameter("message"));
                }});

            List<String> localizedChat = new ArrayList<>();
            chat.forEach(x -> {
                LocalizedUser localizedUser = userService.getUserById(Integer.valueOf(x.get(0))).getLocalizedUsers().get(locale);
                localizedChat.add(localizedUser.getName() + "  " + localizedUser.getSurname() + ": " + x.get(1));
            });
            request.setAttribute("chat", localizedChat);
        }

        Optional<Question> activeQuestion = activeGame.get().getQuestions().stream()
                .reduce((first, second) -> second);

        request.setAttribute("configuration", activeGame.get().getConfiguration().getLocalizedConfigurations().get(locale));
        request.setAttribute("activeGame", activeGame.get());

        if (!activeQuestion.isPresent()) {
            Logger.getLogger(this.getClass()).info("Active Question is not present");
            response.setIntHeader("refresh", 3);
            return "/WEB-INF/player/game/playerGamePhase.jsp";
        }
        request.setAttribute("typeOfQuestionPage", typeOfQuestionPage.get(activeQuestion.get().getTypeOfQuestion()));

        /*if (activeQuestion.get().getPlayerAnswer() != null) {
            response.setIntHeader("refresh", 3);
            request.setAttribute("timeSpent", timeService.calculateTimeForAnswer(activeQuestion.get()));
        }*/

        activeQuestion.get().getHints().forEach(y -> {
            if (y.getTypeOfHint().equals(TypeOfHint.SHOW_HINT))
                request.setAttribute("showHint", "true");
            if (y.getTypeOfHint().equals(TypeOfHint.SHOW_PROBABILITY))
                request.setAttribute("probabilities", hintService.calculateProbabilities());
            if (y.getTypeOfHint().equals(TypeOfHint.REMOVE_CHOICES))
                request.setAttribute("removeChoices", "true");
        });


        if (activeQuestion.get().getPlayerAnswer() == null) {
            int timeLeft = timeService.calculateTimeLeft(activeQuestion.get(), activeGame.get().getConfiguration());
            if (timeLeft > 0) {
                response.setIntHeader("refresh", timeService.calculateTimeToFresh(timeLeft));
            } else {
                activeQuestion.get().setEndTime(new Date().getTime());
                activeQuestion.get().setPlayerAnswer(Constants.noAnswer);
                request.setAttribute("timeSpent", timeService.calculateTimeForAnswer(activeQuestion.get()));
                questionService.updateQuestion(activeQuestion.get());
                Logger.getLogger(this.getClass()).info("Active Question was updated in DB");
            }
            request.setAttribute("time", timeLeft);
        }

        if ((request.getParameter("answerQuestion") != null) && (request.getParameter("answer") != null)
                && !(request.getParameter("answer").isEmpty())) {
            Optional<String> answer = Optional.ofNullable(request.getParameter("answer"));
            answer.ifPresent(x -> {
                activeQuestion.get().setEndTime(new Date().getTime());
                request.setAttribute("timeSpent", timeService.calculateTimeForAnswer(activeQuestion.get()));
                activeQuestion.get().setPlayerAnswer(x);
                questionService.updateQuestion(activeQuestion.get());
                Logger.getLogger(this.getClass()).info("Active Question was update in DB");
            });
            request.removeAttribute("time");
        }

        if (activeQuestion.get().getPlayerAnswer() != null) {
            response.setIntHeader("refresh", 3);
            request.setAttribute("timeSpent", timeService.calculateTimeForAnswer(activeQuestion.get()));
        }

        if ((activeGame.get().getPlayersScore() == activeGame.get().getConfiguration().getMaxScore())
                || (activeGame.get().getSpectatorsScore() == activeGame.get().getConfiguration().getMaxScore())) {
            statisticsService.updateStatistics(activeGame.get());
            Logger.getLogger(this.getClass()).info("Next Phase");
            return nextPhase.executePhase(request, response, Constants.PLAYER_STATISTICS_PHASE);
        }

        request.setAttribute("activeQuestion", activeQuestion.get().getLocaleLocalizedQuestions().get(locale));
        if (activeQuestion.get().getHints() != null) {
            List<LocalizedHint> questionHints = new ArrayList<>();
            activeQuestion.get().getHints()
                    .forEach(x -> questionHints.add(x.getLocalizedHints().get(locale)));
            request.setAttribute("questionHints", questionHints);
        }
        request.setAttribute("activeGame", activeGame.get());

        return "/WEB-INF/player/game/playerGamePhase.jsp";
    }
}
