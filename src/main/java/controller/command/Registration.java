/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.entities.Locales;
import model.entities.user.LocalizedUser;
import model.entities.user.User;
import model.entities.user.Role;
import model.entities.user.Status;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Processes registration command
 */
public class Registration implements Command {

    /**
     * User service
     */
    private UserService userService;

    /**
     * Creates Registration class
     *
     * @param userService User service
     */
    public Registration(UserService userService) {
        Logger.getLogger(this.getClass()).info("Registration class constructor");
        this.userService = userService;
    }

    /**
     * Executes registration command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Registration class, execute method");
        if (request.getParameter("register") == null) {
            Logger.getLogger(this.getClass()).info("No registration");
            return "/WEB-INF/registration/registration.jsp";
        }

        String nameEN = request.getParameter("nameEN");
        String nameUA = request.getParameter("nameUA");
        String surnameEN = request.getParameter("surnameEN");
        String surnameUA = request.getParameter("surnameUA");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (nameEN == null || surnameEN == null || email == null || password == null) {
            Logger.getLogger(this.getClass()).info("Registration Failed");
            return "/WEB-INF/registration/registrationFailed.jsp";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.PLAYER);
        user.setStatus(Status.BUSY);

        LocalizedUser localizedUserEN = new LocalizedUser();
        localizedUserEN.setEmail(email);
        localizedUserEN.setPassword(password);
        localizedUserEN.setRole(Role.PLAYER);
        localizedUserEN.setStatus(Status.BUSY);
        localizedUserEN.setName(nameEN);
        localizedUserEN.setSurname(surnameEN);

        LocalizedUser localizedUserUA = new LocalizedUser();
        localizedUserUA.setEmail(email);
        localizedUserUA.setPassword(password);
        localizedUserUA.setRole(Role.PLAYER);
        localizedUserUA.setStatus(Status.BUSY);
        localizedUserUA.setName(nameUA);
        localizedUserUA.setSurname(surnameUA);

        user.getLocalizedUsers().put(Locales.ENGLISH.getLocale(), localizedUserEN);
        user.getLocalizedUsers().put(Locales.UKRAINIAN.getLocale(), localizedUserUA);

        if (!userService.validateData(user)) {
            Logger.getLogger(this.getClass()).info("User data validation was failed");
            return "/WEB-INF/registration/registrationFailed.jsp";
        }

        userService.addUserToDB(user);
        Logger.getLogger(this.getClass()).info("User : " + user + " was registered");
        return "/WEB-INF/registration/successfulRegistration.jsp";
    }
}
