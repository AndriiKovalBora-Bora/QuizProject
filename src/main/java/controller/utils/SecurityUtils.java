/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.utils;

import controller.config.SecurityConfig;
import model.entities.user.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Provides security utils for page
 */
public class SecurityUtils {

    /**
     * Check if it is needed to be signed up into system to complete request
     *
     * @param request Http request
     * @return True if it is required to sing up to complete request, false otherwise
     */
    public static boolean isSecurityPage(HttpServletRequest request) {
        Logger.getLogger(SecurityUtils.class).info("SecurityUtils class, isSecurityPage method");
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        for (Role role : Role.values()) {
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if request has appropriate role
     *
     * @param request Http request
     * @return True if request has appropriate role, False otherwise
     */
    public static boolean hasPermission(HttpServletRequest request) {
        Logger.getLogger(SecurityUtils.class).info("SecurityUtils class, hasPermission method");
        String urlPattern = UrlPatternUtils.getUrlPattern(request);

        for (Role role : Role.values()) {
            if (!request.isUserInRole(role.name())) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlPatternsForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
