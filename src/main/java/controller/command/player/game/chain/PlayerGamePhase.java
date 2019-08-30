/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import model.constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides abstract class for player game phases
 */
abstract class PlayerGamePhase {

    /**
     * The number of phase
     */
    private int phase;

    /**
     * Next phase
     */
    PlayerGamePhase nextPhase;

    /**
     * Creates phase
     *
     * @param phase The number of phase
     */
    PlayerGamePhase(int phase) {
        Logger.getLogger(this.getClass()).info("PlayerGamePhase class constructor, phase : " + phase);
        this.phase = phase;
    }

    /**
     * Sets next player phase
     *
     * @param playerGamePhase Next player phase
     */
    void setNextPhase(PlayerGamePhase playerGamePhase) {
        Logger.getLogger(this.getClass()).info("Set next phase");
        this.nextPhase = playerGamePhase;
    }

    /**
     * Finds player phase by the number of phase
     *
     * @param request  Http request
     * @param response Http response
     * @param phase    The number of current phase
     * @return JSP to be displayed
     */
    String executePhase(HttpServletRequest request, HttpServletResponse response, int phase) {
        Logger.getLogger(this.getClass()).info("PlayerGamePhase class, execute method");
        if (this.phase == phase) {
            Logger.getLogger(this.getClass()).info("Phase was found, phase : " + phase);
            return execute(request, response);
        }

        if (nextPhase != null) {
            Logger.getLogger(this.getClass()).info("Next phase");
            return nextPhase.executePhase(request, response, phase);
        }

        Logger.getLogger(this.getClass()).error("Phase was not found");
        return Constants.GAME_PHASE_ERROR;
    }

    /**
     * Executes player phase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    abstract String execute(HttpServletRequest request, HttpServletResponse response);
}

