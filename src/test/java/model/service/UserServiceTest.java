/*
 * Copyright (C) 2019 Quiz Project
 */

package model.service;

import model.entities.Locales;
import model.entities.user.LocalizedUser;
import model.entities.user.User;
import model.service.UserService;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Tests UserService
 */
public class UserServiceTest {

    private static UserService userService;

    @BeforeClass
    public static void setUp() {
        userService = new UserService();
    }

    @Test
    public void validateData() {
        User user = new User();
        LocalizedUser userEN = new LocalizedUser();
        userEN.setName("Andrew");
        userEN.setSurname("Koval");

        LocalizedUser userUA = new LocalizedUser();
        userUA.setName("Андрій");
        userUA.setSurname("Коваль");

        user.getLocalizedUsers().put(Locales.ENGLISH.getLocale(), userEN);
        user.getLocalizedUsers().put(Locales.UKRAINIAN.getLocale(), userUA);

        user.setEmail("andrew@gmail.com");
        user.setPassword("andreww");
        assertTrue(userService.validateData(user));

        userUA.setName("Andrew");
        assertFalse(userService.validateData(user));

        userEN.setName("Андрій");
        assertFalse(userService.validateData(user));

        user.setPassword("1");
        assertFalse(userService.validateData(user));

        user.setEmail("email");
        assertFalse(userService.validateData(user));
    }
}