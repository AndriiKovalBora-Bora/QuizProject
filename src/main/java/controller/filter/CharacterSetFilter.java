/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Makes displaying Ukrainian symbols possible
 */
public class CharacterSetFilter implements Filter {

    /**
     * Initializes filter
     *
     * @param filterConfig Filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {
        Logger.getLogger(this.getClass()).info("CharacterSetFilter initialization");
    }

    /**
     * Sets encoding type
     *
     * @param request  Http request
     * @param response Http response
     * @param chain    Filter chain
     * @throws IOException      If error was occurred
     * @throws ServletException If error was occurred
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Logger.getLogger(this.getClass()).info("CharacterSetFilter doFilter");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    /**
     * Destroys filter
     */
    @Override
    public void destroy() {
        Logger.getLogger(this.getClass()).info("CharacterSetFilter destroy");
    }
}
