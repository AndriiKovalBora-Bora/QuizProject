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
 * Tests Login
 */
public class LoginTest {

    @Test
    public void execute() {
        Login login = new Login(new UserService());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));

        assertEquals("/login.jsp", login.execute(request, response));

        when(request.getParameter("loginUser")).thenReturn("value");
        when(request.getParameter("email")).thenReturn("andrew@gmail.com");
        when(request.getParameter("password")).thenReturn("andrew");
        assertEquals("redirect:playerProfile", login.execute(request, response));

        when(request.getParameter("email")).thenReturn("ira@gmail.com");
        when(request.getParameter("password")).thenReturn("ira");
        assertEquals("redirect:administratorProfile", login.execute(request, response));

        when(request.getParameter("password")).thenReturn("wrongPassword");
        assertEquals("/login.jsp", login.execute(request, response));
    }
}