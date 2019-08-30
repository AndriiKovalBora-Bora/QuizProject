/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.statistics.Statistics;
import model.service.ConfigurationService;
import model.service.StatisticsService;
import model.service.UserService;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorConfigurationPhase
 */
public class AdministratorConfigurationPhaseTest {

    @Test
    public void execute() {
        AdministratorConfigurationPhase administratorConfigurationPhase =
                new AdministratorConfigurationPhase(new ConfigurationService(), new UserService(), new StatisticsService(), Constants.ADMINISTRATOR_CONFIGURATION_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/administrator/game/administratorConfigurationPhase.jsp", administratorConfigurationPhase.execute(request, response));

        when(request.getParameter("chooseConfiguration")).thenReturn("value");
        when(request.getParameter("configuration")).thenReturn("1");
        assertEquals("/WEB-INF/view/error.jsp", administratorConfigurationPhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("ira@gmail.com");
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(new ArrayList<Statistics>());
        when(request.getSession().getServletContext().getAttribute("chats")).thenReturn(new HashMap<Integer, ArrayList<ArrayList<String>>>());
        administratorConfigurationPhase.setNextPhase(new AdministratorPlayersPhase(new UserService(), Constants.ADMINISTRATOR_PLAYERS_PHASE));
        assertEquals("/WEB-INF/administrator/game/administratorPlayersPhase.jsp", administratorConfigurationPhase.execute(request, response));
    }
}