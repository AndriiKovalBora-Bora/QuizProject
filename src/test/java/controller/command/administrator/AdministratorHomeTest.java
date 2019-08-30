/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator;

import model.service.StatisticsService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests AdministratorHome
 */
public class AdministratorHomeTest {

    @Test
    public void execute() {
        AdministratorHome administratorHome = new AdministratorHome(new StatisticsService());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("currentPage")).thenReturn("1");
        assertEquals("/WEB-INF/administrator/administratorHome.jsp", administratorHome.execute(request, response));
    }
}