/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides method for making Url patter from http request
 */
public class UrlPatternUtils {

    /**
     * Makes Url patter from http request
     * @param request Http request
     * @return Url pattern
     */
    public static String getUrlPattern(HttpServletRequest request){
        Logger.getLogger(UrlPatternUtils.class).info("UrlPatternsUtils class, getUrlPattern method");
        return request.getRequestURI().replaceAll(".*/app/", "");
    }
}
