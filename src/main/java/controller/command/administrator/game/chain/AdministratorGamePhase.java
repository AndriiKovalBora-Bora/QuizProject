/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides abstract class for administrator game phases
 */
abstract class AdministratorGamePhase {

    /**
     * The number of phase
     */
    private int phase;

    /**
     * Next phase
     */
    AdministratorGamePhase nextPhase;

    /**
     * Creates phase
     *
     * @param phase The number of phase
     */
    AdministratorGamePhase(int phase) {
        Logger.getLogger(this.getClass()).info("AdministratorGamePhase class constructor");
        this.phase = phase;
    }

    /**
     * Sets next administrator phase
     *
     * @param nextPhase Next player phase
     */
    void setNextPhase(AdministratorGamePhase nextPhase) {
        Logger.getLogger(this.getClass()).info("Set next phase");
        this.nextPhase = nextPhase;
    }

    /**
     * Finds administrator phase by the number of phase
     *
     * @param request  Http request
     * @param response Http response
     * @param phase    The number of current phase
     * @return JSP to be displayed
     */
    String executePhase(HttpServletRequest request, HttpServletResponse response, int phase) {
        Logger.getLogger(this.getClass()).info("AdministratorGamePhase class, executePhase method");
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
     * Executes administrator phase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    abstract String execute(HttpServletRequest request, HttpServletResponse response);
}
