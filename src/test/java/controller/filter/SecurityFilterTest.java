/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.filter;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests SecurityFilter
 */
public class SecurityFilterTest {
    private static SecurityFilter securityFilter;

    @BeforeClass
    public static void setUp() {
        securityFilter = new SecurityFilter();
    }

    @Test
    public void init() {
        securityFilter.init(new FilterConfig() {
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
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/app/login");
        securityFilter.doFilter(request, response, filterChain);

        when(request.getSession().getAttribute("email")).thenReturn("andrew@gmail.com");
        when(request.getSession().getServletContext().getRequestDispatcher("/WEB-INF/view/accessDeniedView.jsp")).thenReturn(new RequestDispatcher() {
            @Override
            public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {

            }

            @Override
            public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {

            }
        });
        securityFilter.doFilter(request, response, filterChain);
    }

    @Test
    public void destroy() {
        securityFilter.destroy();
    }
}