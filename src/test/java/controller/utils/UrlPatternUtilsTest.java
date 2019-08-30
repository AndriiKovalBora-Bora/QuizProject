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
 * Tests UrlPatternUtils
 */
public class UrlPatternUtilsTest {

    @Test
    public void getUrlPattern() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/app/playerProfile");
        assertEquals("playerProfile", UrlPatternUtils.getUrlPattern(request));
    }
}