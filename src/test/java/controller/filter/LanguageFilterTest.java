/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.filter;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests LanguageFilter
 */
public class LanguageFilterTest {
    private static LanguageFilter languageFilter;

    @BeforeClass
    public static void setUp() {
        languageFilter = new LanguageFilter();
    }

    @Test
    public void init() {
        languageFilter.init(new FilterConfig() {
            @Override
            public String getFilterName() {
                return null;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public String getInitParameter(String name) {
                return null;
            }

            @Override
            public Enumeration getInitParameterNames() {
                return null;
            }
        });
    }

    @Test
    public void doFilter() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        FilterChain filterChain = mock(FilterChain.class);
        languageFilter.doFilter(request, response, filterChain);

        when(request.getParameter("language")).thenReturn("ENGLISH");
        languageFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void destroy() {
        languageFilter.destroy();
    }
}