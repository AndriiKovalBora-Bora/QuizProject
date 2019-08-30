/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.configuration.LocalizedConfiguration;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.service.ConfigurationService;
import model.service.StatisticsService;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes AdministratorConfigurationPhase
 */
public class AdministratorConfigurationPhase extends AdministratorGamePhase implements Command {

    /**
     * Configuration service
     */
    private ConfigurationService configurationService;

    /**
     * User service
     */
    private UserService userService;

    /**
     * Statistics service
     */
    private StatisticsService statisticsService;

    /**
     * Creates AdministratorConfigurationPhase class
     *
     * @param configurationService Configuration service
     * @param userService          User service
     * @param statisticsService    Statistics service
     * @param phase                The number of current phase
     */
    AdministratorConfigurationPhase(ConfigurationService configurationService, UserService userService, StatisticsService statisticsService, int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("AdministratorConfigurationPhase class constructor");
        this.configurationService = configurationService;
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    /**
     * Executes AdministratorConfigurationPhase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("AdministratorConfigurationPhase class, execute method");
        request.getSession().setAttribute("phase", Constants.ADMINISTRATOR_CONFIGURATION_PHASE);
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        List<LocalizedConfiguration> configurations = configurationService
                .getAllConfiguration()
                .stream()
                .map(x -> x.getLocalizedConfigurations().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("configurations", configurations);

        if ((request.getParameter("chooseConfiguration") != null)
                && (request.getParameter("configuration") != null)) {

            Statistics activeGame = new Statistics();
            activeGame.setConfiguration(configurationService.getConfigurationById(request.getParameter("configuration")));

            Optional<User> administrator = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
            if (!administrator.isPresent()) {
                Logger.getLogger(this.getClass()).error("Administrator is not present");
                return "/WEB-INF/view/error.jsp";
            }

            activeGame.setAdministrator(administrator.get());
            activeGame.setId(statisticsService.addStatisticsToDB(activeGame));
            ((ArrayList<Statistics>) (request.getSession().getServletContext()
                    .getAttribute("activeGames")))
                    .add(activeGame);

            ArrayList<ArrayList<String>> chat = new ArrayList<>();
            ((HashMap<Integer, ArrayList<ArrayList<String>>>) (request.getSession().getServletContext()
                    .getAttribute("chats"))).put(activeGame.getId(), chat);
            Logger.getLogger(this.getClass()).info("Next phase : ADMINISTRATOR_PLAYERS_PHASE");
            return nextPhase.executePhase(request, response, Constants.ADMINISTRATOR_PLAYERS_PHASE);
        }

        return "/WEB-INF/administrator/game/administratorConfigurationPhase.jsp";
    }
}
