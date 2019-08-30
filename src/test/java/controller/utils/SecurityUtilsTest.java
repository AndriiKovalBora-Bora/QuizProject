/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests SecurityUtils
 */
public class SecurityUtilsTest {
    @Test
    public void isSecurityPage() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/app/login");
        assertTrue(SecurityUtils.isSecurityPage(request));

        when(request.getRequestURI()).thenReturn("/app/anotherPage");
        assertFalse(SecurityUtils.isSecurityPage(request));
    }

    @Test
    public void hasPermission() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/app/login");
        assertFalse(SecurityUtils.hasPermission(request));
    }
}