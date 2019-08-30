/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.question.LocalizedQuestion;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.entities.configuration.StatisticsFormat;
import model.entities.user.Status;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes AdministratorStatisticsPhase
 */
public class AdministratorStatisticsPhase extends AdministratorGamePhase implements Command {

    /**
     * User service
     */
    private UserService userService = new UserService();

    /**
     * Statistics formats for displaying statistics
     */
    private Map<StatisticsFormat, String> statisticsFormat = new HashMap<>();

    /**
     * Creates AdministratorStatisticsPhase class
     *
     * @param phase The number of current game phase
     */
    AdministratorStatisticsPhase(int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("AdministratorStatisticsPhase class constructor");
        statisticsFormat.put(StatisticsFormat.SHORT, "/WEB-INF/statistics.format/statisticsShort.jsp");
        statisticsFormat.put(StatisticsFormat.LONG, "/WEB-INF/statistics.format/statisticsLong.jsp");
    }

    /**
     * Executes AdministratorStatisticsPhase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("AdministratorStatisticsPhase class, execute method");
        request.getSession().setAttribute("phase", Constants.ADMINISTRATOR_STATISTICS_PHASE);
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        List<Statistics> activeGames = ((ArrayList<Statistics>) request.getSession().getServletContext().getAttribute("activeGames"));

        Optional<User> administrator = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!administrator.isPresent()) {
            Logger.getLogger(this.getClass()).error("Administrator is not present");
            return "/WEB-INF/view/error.jsp";
        }

        Optional<Statistics> activeGame = activeGames.stream().filter(x -> x.getAdministrator().equals(administrator.get()))
                .findAny();
        if (!activeGame.isPresent()) {
            Logger.getLogger(this.getClass()).info("Game is not present");
            return "/WEB-INF/view/error.jsp";
        }

        request.setAttribute("lastGame", activeGame.get());
        List<LocalizedQuestion> questions = activeGame
                .get()
                .getQuestions()
                .stream()
                .map(x -> x.getLocaleLocalizedQuestions().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("questions", questions);
        request.setAttribute("statisticsFormat", statisticsFormat.getOrDefault(activeGame.get().getConfiguration().getStatisticsFormat(), "/WEB-INF/statisticsFormat/statisticsShort.jsp"));

        if (request.getParameter("newGame") != null) {
            activeGame.get().setAdministrator(null);
            administrator.get().setStatus(Status.BUSY);
            userService.updateUser(administrator.get());

            if (activeGame.get().getPlayers().isEmpty() && (activeGame.get().getAdministrator() == null))
                activeGames.remove(activeGame.get());
            Logger.getLogger(this.getClass()).info("Next phase : ADMINISTRATOR_CONFIGURATION_PHASE");
            return nextPhase.executePhase(request, response, Constants.ADMINISTRATOR_CONFIGURATION_PHASE);
        }
        return "/WEB-INF/administrator/game/administratorStatisticsPhase.jsp";
    }
}
