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
 * Tests Registration
 */
public class RegistrationTest {

    @Test
    public void execute() {
        Registration registration = new Registration(new UserService());

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        assertEquals("/WEB-INF/registration/registration.jsp", registration.execute(request, response));

        when(request.getParameter("register")).thenReturn("value");
        when(request.getParameter("nameEN")).thenReturn("testNameEN");
        when(request.getParameter("nameUA")).thenReturn("тестІм'я");
        when(request.getParameter("surnameEN")).thenReturn("testSurnameEN");
        when(request.getParameter("surnameUA")).thenReturn("тестПрізвище");
        when(request.getParameter("email")).thenReturn("testEmail@gmail.com");
        assertEquals("/WEB-INF/registration/registrationFailed.jsp", registration.execute(request, response));

        when(request.getParameter("password")).thenReturn("testPassword");
        when(request.getParameter("nameEN")).thenReturn("111");
        assertEquals("/WEB-INF/registration/registrationFailed.jsp", registration.execute(request, response));
    }
}