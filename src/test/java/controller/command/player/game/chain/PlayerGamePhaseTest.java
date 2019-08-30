/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

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
 * Tests PlayerGamePhase
 */
public class PlayerGamePhaseTest {
    private static PlayerGamePhase playerGamePhase;

    @BeforeClass
    public static void setUp() {
        playerGamePhase = new PlayerGamePhase(Constants.PLAYER_GAME_PHASE) {
            @Override
            String execute(HttpServletRequest request, HttpServletResponse response) {
                return "executed";
            }
        };
    }

    @Test
    public void setNextPhase() {
        playerGamePhase.setNextPhase(new PlayerStatisticsPhase(new UserService(), Constants.PLAYER_STATISTICS_PHASE));
    }

    @Test
    public void executePhase() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        assertEquals("executed", playerGamePhase.executePhase(request, response, Constants.PLAYER_GAME_PHASE));
        playerGamePhase.setNextPhase(null);
        assertEquals(Constants.GAME_PHASE_ERROR, playerGamePhase.executePhase(request, response, Constants.PLAYER_STATISTICS_PHASE));
    }
}