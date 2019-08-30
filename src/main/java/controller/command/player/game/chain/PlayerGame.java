/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Presents player game process
 */
public class PlayerGame implements Command {

    /**
     * Executes player game
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("PlayerGame class, execute class");
        PlayerPlayingGamePhase playerPlayingGamePhase = new PlayerPlayingGamePhase(new UserService(), new TimeService(), new HintService(), new StatisticsService(), new QuestionService(), Constants.PLAYER_GAME_PHASE);
        PlayerStatisticsPhase playerEndGamePhase = new PlayerStatisticsPhase(new UserService(), Constants.PLAYER_STATISTICS_PHASE);

        playerPlayingGamePhase.setNextPhase(playerEndGamePhase);
        playerEndGamePhase.setNextPhase(playerPlayingGamePhase);

        if (request.getSession().getAttribute("phase") == null) {
            Logger.getLogger(this.getClass()).info("Player game phase is null, setting to PLAYER_GAME_PHASE");
            request.getSession().setAttribute("phase", Constants.PLAYER_GAME_PHASE);
        }

        int phase = (int) request.getSession().getAttribute("phase");
        return playerPlayingGamePhase.executePhase(request, response, phase);
    }
}
