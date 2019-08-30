/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.listener;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests ContextListener
 */
public class ContextListenerTest {
    private static ContextListener contextListener;
    private static ServletContextEvent sce;
    @BeforeClass
    public static void setUp() {
        contextListener = new ContextListener();
        sce = mock(ServletContextEvent.class);
    }

    @Test
    public void contextInitialized() {
        when(sce.getServletContext()).thenReturn(mock(ServletContext.class));
        contextListener.contextInitialized(sce);
    }

    @Test
    public void contextDestroyed() {
        contextListener.contextDestroyed(sce);
    }
}