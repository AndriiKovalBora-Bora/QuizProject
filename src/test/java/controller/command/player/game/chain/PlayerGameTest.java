/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.statistics.Statistics;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * PlayerGame
 */
public class PlayerGameTest {

    @Test
    public void execute() {
        PlayerGame playerGame = new PlayerGame();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        when(request.getSession().getAttribute("phase")).thenReturn(Constants.PLAYER_GAME_PHASE);
        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        when(request.getSession().getAttribute("email")).thenReturn("andrew@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/player/game/playerGamePhase.jsp", playerGame.execute(request, response));
    }
}