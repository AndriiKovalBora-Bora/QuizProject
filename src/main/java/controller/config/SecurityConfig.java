/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.config;

import model.entities.user.Role;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Provides security configuration for the project
 */
public class SecurityConfig {

    /**
     * Contains JSP and roles that have access to relevant pages
     */
    private static final Map<Role, List<String>> mapConfig = new HashMap<>();

    /**
     * Static initialization
     */
    static {
        init();
    }

    /**
     * Assigns pages to related role
     */
    private static void init() {
        Logger.getLogger(SecurityConfig.class).info("SecurityConfig class initialization");
        List<String> urlPatterns1 = new ArrayList<>();
        urlPatterns1.add("playerHome");
        urlPatterns1.add("playerProfile");
        urlPatterns1.add("playerGame");
        mapConfig.put(Role.PLAYER, urlPatterns1);

        List<String> urlPatterns2 = new ArrayList<>();
        urlPatterns2.add("administratorHome");
        urlPatterns2.add("administratorProfile");
        urlPatterns2.add("administratorGame");
        urlPatterns2.add("administratorConfiguration");
        mapConfig.put(Role.ADMINISTRATOR, urlPatterns2);

        List<String> urlPatterns3 = new ArrayList<>();
        urlPatterns3.add("login");
        urlPatterns3.add("registration");
        urlPatterns3.add("index");
        mapConfig.put(Role.VISITOR, urlPatterns3);
    }

    /**
     * Returns pages according to the role
     * @param role Role
     * @return List of pages
     */
    public static List<String> getUrlPatternsForRole(Role role) {
        return mapConfig.get(role);
    }
}
