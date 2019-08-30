/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator;

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
 * Processes AdministratorProfile command
 */
public class AdministratorProfile implements Command {

    /**
     * Statistics service
     */
    private StatisticsService statisticsService;

    /**
     * User service
     */
    private UserService userService;

    /**
     * Creates PlayerProfile class
     *
     * @param statisticsService Statistics servoce
     * @param userService       User service
     */
    public AdministratorProfile(StatisticsService statisticsService, UserService userService) {
        Logger.getLogger(this.getClass()).info("AdministratorProfile class constructor");
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    /**
     * Executes administratorProfile command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Administrator class, execute method");
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        Optional<User> administrator = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!administrator.isPresent()) return "/WEB-INF/view/error.jsp";

        List<Statistics> statisticsHistory = statisticsService.getUserStatistics(administrator.get());
        request.setAttribute("administrator", administrator.get().getLocalizedUsers().get(locale));
        request.setAttribute("statisticsHistory", statisticsHistory);
        return "/WEB-INF/administrator/administratorProfile.jsp";
    }
}
