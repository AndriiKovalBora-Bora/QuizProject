/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Presents administrator game process
 */
public class AdministratorGame implements Command {

    /**
     * Executes administrator game
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("AdministratorGame class, execute method");
        AdministratorGamePhase configurationPhase = new AdministratorConfigurationPhase(new ConfigurationService(), new UserService(), new StatisticsService(), Constants.ADMINISTRATOR_CONFIGURATION_PHASE);
        AdministratorGamePhase playerPhase = new AdministratorPlayersPhase(new UserService(), Constants.ADMINISTRATOR_PLAYERS_PHASE);
        AdministratorGamePhase playingGame = new AdministratorPlayingGamePhase(new QuestionService(), new UserService(), new TimeService(), new HintService(), new StatisticsService(), Constants.ADMINISTRATOR_GAME_PHASE);
        AdministratorGamePhase endGame = new AdministratorStatisticsPhase(Constants.ADMINISTRATOR_STATISTICS_PHASE);

        configurationPhase.setNextPhase(playerPhase);
        playerPhase.setNextPhase(playingGame);
        playingGame.setNextPhase(endGame);
        endGame.setNextPhase(configurationPhase);

        if (request.getSession().getAttribute("phase") == null) {
            Logger.getLogger(this.getClass()).info("Administrator game phase is null, setting to ADMINISTRATOR_CONFIGURATION_PHASE");
            request.getSession().setAttribute("phase", Constants.ADMINISTRATOR_CONFIGURATION_PHASE);
        }

        int phase = (int) request.getSession().getAttribute("phase");
        return configurationPhase.executePhase(request, response, phase);
    }
}
