/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.statistics.Statistics;
import model.service.*;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorPlayersPhase
 */
public class AdministratorPlayersPhaseTest {

    @Test
    public void execute() {
        AdministratorPlayersPhase administratorPlayersPhase =
                new AdministratorPlayersPhase(new UserService(), Constants.ADMINISTRATOR_PLAYERS_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/view/error.jsp", administratorPlayersPhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("ira@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/view/error.jsp", administratorPlayersPhase.execute(request, response));

        Statistics activeGame = new Statistics();
        activeGame.setId(1);
        activeGame.setAdministrator(new UserService().getUserByEmail("ira@gmail.com").get());
        activeGame.setConfiguration(new Configuration());
        activeGames.add(activeGame);
        assertEquals("/WEB-INF/administrator/game/administratorPlayersPhase.jsp", administratorPlayersPhase.execute(request, response));

        HashMap<Integer, ArrayList<ArrayList<String>>> chats = new HashMap<>();
        ArrayList<ArrayList<String>> chat = new ArrayList<>();
        chats.put(1, chat);
        when(request.getSession().getServletContext().getAttribute("chats")).thenReturn(chats);
        when(request.getParameter("choosePlayers")).thenReturn("value");
        when(request.getParameterValues("gamePlayers")).thenReturn(new String[0]);
        when(request.getParameterValues("gamePlayers")).thenReturn(new String[0]);
        administratorPlayersPhase.setNextPhase(new AdministratorPlayingGamePhase(new QuestionService(), new UserService(), new TimeService(), new HintService(), new StatisticsService(), Constants.ADMINISTRATOR_GAME_PHASE));
        assertEquals("/WEB-INF/administrator/game/administratorGamePhase.jsp", administratorPlayersPhase.execute(request, response));
    }
}