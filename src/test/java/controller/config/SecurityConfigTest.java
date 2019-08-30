/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.config;

import model.entities.user.Role;
import org.junit.Test;

import java.security.Security;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests SecurityConfig
 */
public class SecurityConfigTest {

    @Test
    public void getUrlPatternsForRole() {
        List<String> visitorPages = SecurityConfig.getUrlPatternsForRole(Role.VISITOR);
        String pages = visitorPages.stream().reduce("", (acc, x) -> acc + x + ", ");
        pages = pages.substring(0, pages.length() - 2);
        assertEquals("login, registration, index", pages);

        List<String> playerPages = SecurityConfig.getUrlPatternsForRole(Role.PLAYER);
        pages = playerPages.stream().reduce("", (acc, x) -> acc + x + ", ");
        pages = pages.substring(0, pages.length() - 2);
        assertEquals("playerHome, playerProfile, playerGame", pages);

        List<String> administratorPages = SecurityConfig.getUrlPatternsForRole(Role.ADMINISTRATOR);
        pages = administratorPages.stream().reduce("", (acc, x) -> acc + x + ", ");
        pages = pages.substring(0, pages.length() - 2);
        assertEquals("administratorHome, administratorProfile, administratorGame, administratorConfiguration", pages);
    }
}