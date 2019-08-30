/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.request;

import model.entities.user.Role;
import model.entities.user.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.FactoryConfigurationError;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Tests UserRoleRequestWrapper
 */
public class UserRoleRequestWrapperTest {

    @Test
    public void isUserInRole() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserRoleRequestWrapper userRoleRequestWrapper = new UserRoleRequestWrapper("andrew@gmail.com", Role.VISITOR, request);
        assertTrue(userRoleRequestWrapper.isUserInRole("VISITOR"));
        assertFalse(userRoleRequestWrapper.isUserInRole("PLAYER"));
    }

    @Test
    public void getUserPrincipal() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserRoleRequestWrapper userRoleRequestWrapper = new UserRoleRequestWrapper("andrew@gmail.com", Role.VISITOR, request);
        userRoleRequestWrapper.getUserPrincipal();
    }
}