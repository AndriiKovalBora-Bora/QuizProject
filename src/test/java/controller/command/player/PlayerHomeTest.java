/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player;

import model.service.StatisticsService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests PlayerHome
 */
public class PlayerHomeTest {

    @Test
    public void execute() {
        PlayerHome playerHome = new PlayerHome(new StatisticsService());
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("currentPage")).thenReturn("1");
        assertEquals("/WEB-INF/player/playerHome.jsp", playerHome.execute(request, response));
    }
}