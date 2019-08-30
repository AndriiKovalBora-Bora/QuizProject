/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorGame
 */
public class AdministratorGameTest {

    @Test
    public void execute() {
        AdministratorGame administratorGame = new AdministratorGame();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));

        when(request.getSession().getAttribute("phase")).thenReturn(Constants.ADMINISTRATOR_CONFIGURATION_PHASE);
        assertEquals("/WEB-INF/administrator/game/administratorConfigurationPhase.jsp", administratorGame.execute(request, response));
    }
}