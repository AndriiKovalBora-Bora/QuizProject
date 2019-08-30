/*
 * Copyright (C) 2019 Quiz Project
 */

package controller;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

/**
 * Test Servlet
 */
public class ServletTest {
    private static Servlet servlet;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static RequestDispatcher requestDispatcher;

    @BeforeClass
    public static void setUp() {
        servlet = new Servlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void init() {
        servlet.init();
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/app/login");
        when(request.getRequestDispatcher("/login.jsp")).thenReturn(requestDispatcher);
        servlet.init();
        servlet.doGet(request, response);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/app/registration");
        when(request.getRequestDispatcher("/WEB-INF/registration/registration.jsp")).thenReturn(requestDispatcher);
        servlet.init();
        servlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }
}