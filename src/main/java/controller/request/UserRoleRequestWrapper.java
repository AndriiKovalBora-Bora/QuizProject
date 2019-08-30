/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.request;

import model.entities.user.Role;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * Wraps request
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {

    /**
     * User's email
     */
    private String user;

    /**
     * User's role
     */
    private Role role;

    /**
     * Request without wrapping
     */
    private HttpServletRequest realRequest;

    /**
     * Creates wrapped request
     * @param user User's email
     * @param role User's role
     * @param request Request
     */
    public UserRoleRequestWrapper(String user, Role role, HttpServletRequest request) {
        super(request);
        Logger.getLogger(this.getClass()).info("UserRoleRequestWrapper class constructor");
        this.user = user;
        this.role = role;
        this.realRequest = request;
    }

    /**
     * Checks if user has appropriate role to the requested page
     * @param role User's role
     */
    @Override
    public boolean isUserInRole(String role) {
        Logger.getLogger(this.getClass()).info("UserRoleRequestWrapper class, isUserInRole method");
        if (role == null) {
            return this.realRequest.isUserInRole(null);
        }
        return this.role == Role.valueOf(role);
    }

    /**
     * Gets principal
     * @return Principal
     */
    @Override
    public Principal getUserPrincipal() {
        Logger.getLogger(this.getClass()).info("UserRoleRequestWrapper class, getUserPrincipal method");
        if (this.user == null) {
            return realRequest.getUserPrincipal();
        }
        return () -> user;
    }
}
