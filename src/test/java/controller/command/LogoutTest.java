/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests Logout
 */
public class LogoutTest {

    @Test
    public void execute() {
        Logout logout = new Logout(new UserService());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));

        assertEquals("redirect:login", logout.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("andrew@gmail.com");
        assertEquals("redirect:login", logout.execute(request, response));
    }
}