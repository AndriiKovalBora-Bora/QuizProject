/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.entities.configuration.StatisticsFormat;
import model.entities.user.Status;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Processes PlayerStatisticsPhase
 */
public class PlayerStatisticsPhase extends PlayerGamePhase implements Command {

    /**
     * User service
     */
    private UserService userService;

    /**
     * Statistics formats for displaying statistics
     */
    private Map<StatisticsFormat, String> statisticsFormat = new HashMap<>();

    /**
     * Creates PLayerStatisticsPhase class
     *
     * @param userService User service
     * @param phase The number of current game phase
     */
    PlayerStatisticsPhase(UserService userService, int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("PlayerStatisticsPhase class, constructor");
        this.userService = userService;
        statisticsFormat.put(StatisticsFormat.SHORT, "/WEB-INF/statistics.format/statisticsShort.jsp");
        statisticsFormat.put(StatisticsFormat.LONG, "/WEB-INF/statistics.format/statisticsLong.jsp");
    }

    /**
     * Executes PlayerStatisticsPhase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("PlayerStatisticsPhase class, execute method");
        request.getSession().setAttribute("phase", Constants.PLAYER_STATISTICS_PHASE);
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
            Logger.getLogger(this.getClass()).error("Game is not present");
            return "/WEB-INF/view/error.jsp";
        }

        request.setAttribute("configuration", activeGame.get().getConfiguration().getLocalizedConfigurations().get(locale));
        request.setAttribute("lastGame", activeGame.get());
        request.setAttribute("statisticsFormat", statisticsFormat.getOrDefault(activeGame.get().getConfiguration().getStatisticsFormat(), "/WEB-INF/statisticsFormat/statisticsShort.jsp"));

        if (request.getParameter("finish") == null) {
            Logger.getLogger(this.getClass()).info("Finish game");
            return "/WEB-INF/player/game/playerStatisticsPhase.jsp";
        }

        activeGame.get().getPlayers().remove(player.get());
        player.get().setStatus(Status.FREE);
        userService.updateUser(player.get());
        Logger.getLogger(this.getClass()).info("User : " + player.get() + "was updated in DB");

        if (activeGame.get().getPlayers().isEmpty() && (activeGame.get().getAdministrator() == null))
            activeGames.remove(activeGame.get());

        return nextPhase.executePhase(request, response, Constants.PLAYER_GAME_PHASE);
    }
}
