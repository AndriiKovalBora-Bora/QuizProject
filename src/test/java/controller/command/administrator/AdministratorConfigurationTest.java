/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator;

import model.entities.Locales;
import model.service.ConfigurationService;
import model.service.HintService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorConfiguration
 */
public class AdministratorConfigurationTest {

    @Test
    public void execute() {
        AdministratorConfiguration administratorConfiguration
                = new AdministratorConfiguration(new ConfigurationService(), new HintService());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());

        assertEquals("/WEB-INF/administrator/administratorConfiguration.jsp", administratorConfiguration.execute(request, response));

        when(request.getParameter("statisticsFormat")).thenReturn("LONG");
        when(request.getParameter("newConfiguration")).thenReturn("values");
        when(request.getParameter("time")).thenReturn("10");
        when(request.getParameter("numberOfPlayers")).thenReturn("10");
        when(request.getParameter("maxScore")).thenReturn("10");
        when(request.getParameter("numberOfHints")).thenReturn("10");
        when(request.getParameterValues("hintsWithoutChoices")).thenReturn(new String[]{"1"});
        when(request.getParameterValues("hintsWithChoices")).thenReturn(new String[]{"3"});

        assertEquals("/WEB-INF/administrator/administratorConfiguration.jsp", administratorConfiguration.execute(request, response));
    }
}