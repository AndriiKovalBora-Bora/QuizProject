/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player;

import controller.command.Command;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.service.StatisticsService;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Processes PlayerProfile command
 */
public class PlayerProfile implements Command {
    private StatisticsService statisticsService;
    private UserService userService;

    /**
     * Creates PlayerProfile class
     *
     * @param statisticsService Statistics service
     * @param userService       User service
     */
    public PlayerProfile(StatisticsService statisticsService, UserService userService) {
        Logger.getLogger(this.getClass()).info("PlayerProfile class, constructor");
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    /**
     * Executes playerProfile command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("PlayerProfile class, execute method");
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        Optional<User> player = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!player.isPresent()) {
            Logger.getLogger(this.getClass()).error("Player is not present");
            return "/WEB-INF/view/error.jsp";
        }

        request.setAttribute("player", player.get().getLocalizedUsers().get(locale));

        List<Statistics> statisticsHistory = statisticsService.getUserStatistics(player.get());
        request.setAttribute("statisticsHistory", statisticsHistory);
        return "/WEB-INF/player/playerProfile.jsp";
    }
}
