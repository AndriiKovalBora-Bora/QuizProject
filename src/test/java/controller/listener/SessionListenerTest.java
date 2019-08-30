/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.listener;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests SessionListener
 */
public class SessionListenerTest {
    private static SessionListener sessionListener;
    private static HttpSessionEvent se;

    @BeforeClass
    public static void setUp() {
        sessionListener = mock(SessionListener.class);
        se = mock(HttpSessionEvent.class);
    }

    @Test
    public void sessionCreated() {
        sessionListener.sessionCreated(se);
    }

    @Test
    public void sessionDestroyed() {
        when(se.getSession()).thenReturn(mock(HttpSession.class));
        when(se.getSession().getServletContext()).thenReturn(mock(ServletContext.class));
        when(se.getSession().getServletContext().getAttribute("loggedUsers"))
                .thenReturn(new HashSet<>(Collections.singletonList("andrew@gmail.com")));
        sessionListener.sessionDestroyed(se);
    }
}