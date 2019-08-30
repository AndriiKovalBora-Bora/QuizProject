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

import java.io.IOException;
import java.util.Enumeration;

import static org.mockito.Mockito.mock;

/**
 * Tests CharacterSetFilter
 */
public class CharacterSetFilterTest {
    private static CharacterSetFilter characterSetFilter;

    @BeforeClass
    public static void setUp() {
        characterSetFilter = new CharacterSetFilter();
    }

    @Test
    public void init() {
        characterSetFilter.init(new FilterConfig() {
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
        FilterChain filterChain = mock(FilterChain.class);
        characterSetFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void destroy() {
        characterSetFilter.destroy();
    }
}