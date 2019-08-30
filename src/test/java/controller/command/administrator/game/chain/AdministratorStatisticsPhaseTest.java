/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.configuration.Configuration;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorStatisticsPhase
 */
public class AdministratorStatisticsPhaseTest {

    @Test
    public void execute() {
        AdministratorStatisticsPhase administratorStatisticsPhase =
                new AdministratorStatisticsPhase(Constants.ADMINISTRATOR_STATISTICS_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/view/error.jsp", administratorStatisticsPhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("ira@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/view/error.jsp", administratorStatisticsPhase.execute(request, response));

        Statistics activeGame = new Statistics();
        activeGame.setId(1);
        activeGame.setAdministrator(new UserService().getUserByEmail("ira@gmail.com").get());
        activeGame.setConfiguration(new Configuration());
        activeGames.add(activeGame);
        assertEquals("/WEB-INF/administrator/game/administratorStatisticsPhase.jsp", administratorStatisticsPhase.execute(request, response));

        when(request.getParameter("newGame")).thenReturn("value");
        administratorStatisticsPhase.setNextPhase(new AdministratorConfigurationPhase(new ConfigurationService(), new UserService(), new StatisticsService(), Constants.ADMINISTRATOR_CONFIGURATION_PHASE));
        assertEquals("/WEB-INF/administrator/game/administratorConfigurationPhase.jsp", administratorStatisticsPhase.execute(request, response));
    }
}