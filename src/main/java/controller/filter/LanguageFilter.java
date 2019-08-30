/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.filter;

import model.entities.Locales;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

/**
 * Provides localization of JSPs
 */
public class LanguageFilter implements Filter {

    /**
     * Initializes filter
     *
     * @param filterConfig Filter configuration
     */
    @Override
    public void init(FilterConfig filterConfig) {
        Logger.getLogger(this.getClass()).info("LanguageFilter initialization");
    }

    /**
     * Reads and sets current locale
     *
     * @param request  Http request
     * @param response Http response
     * @param chain    Filter chain
     * @throws IOException      If error was occurred
     * @throws ServletException If error was occurred
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Logger.getLogger(this.getClass()).info("LanguageFilter doFilter");
        HashMap<String, Locale> locales = new HashMap<>();
        locales.put("english", Locales.ENGLISH.getLocale());
        locales.put("ukrainian", Locales.UKRAINIAN.getLocale());

        HttpServletRequest req = (HttpServletRequest) request;
        if (request.getParameter("language") != null) {
            req.getSession().setAttribute("locale", locales.get(request.getParameter("language")));
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroys filter
     */
    @Override
    public void destroy() {
        Logger.getLogger(this.getClass()).info("LanguageFilter destroy");
    }
}
