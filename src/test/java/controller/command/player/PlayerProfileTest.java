/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player;

import model.entities.Locales;
import model.service.StatisticsService;
import model.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests PlayerProfile
 */
public class PlayerProfileTest {

    @Test
    public void execute() {
        PlayerProfile playerProfile =
                new PlayerProfile(new StatisticsService(), new UserService());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());

        assertEquals("/WEB-INF/view/error.jsp", playerProfile.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("andrew@gmail.com");
        assertEquals("/WEB-INF/player/playerProfile.jsp", playerProfile.execute(request, response));
    }
}