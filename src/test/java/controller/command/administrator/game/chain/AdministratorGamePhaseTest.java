/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import model.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests AdministratorGamePhase
 */
public class AdministratorGamePhaseTest {
    private static AdministratorGamePhase administratorGamePhase;

    @BeforeClass
    public static void setUp() {
        administratorGamePhase = new AdministratorGamePhase(Constants.ADMINISTRATOR_CONFIGURATION_PHASE) {
            @Override
            String execute(HttpServletRequest request, HttpServletResponse response) {
                return "executed";
            }
        };
    }

    @Test
    public void setNextPhase() {
        administratorGamePhase.setNextPhase(new AdministratorPlayersPhase(new UserService(), Constants.ADMINISTRATOR_PLAYERS_PHASE));
    }

    @Test
    public void executePhase() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        assertEquals("executed", administratorGamePhase.executePhase(request, response, Constants.ADMINISTRATOR_CONFIGURATION_PHASE));
        administratorGamePhase.setNextPhase(null);
        assertEquals(Constants.GAME_PHASE_ERROR, administratorGamePhase.executePhase(request, response, Constants.ADMINISTRATOR_PLAYERS_PHASE));
    }
}