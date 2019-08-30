/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.entities.user.User;
import model.entities.user.Status;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;

/**
 * Processes logout command
 */
public class Logout implements Command {

    /**
     * User service
     */
    private UserService userService;

    /**
     * Creates Logout class
     *
     * @param userService User service
     */
    public Logout(UserService userService) {
        Logger.getLogger(this.getClass()).info("Logout class constructor");
        this.userService = userService;
    }

    /**
     * Executes logout command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Logout class, execute method");
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        Optional<String> email = Optional.ofNullable((String) request.getSession().getAttribute("email"));
        email.ifPresent(x -> {
            Optional<User> user = userService.getUserByEmail(x);
            user.ifPresent(y -> {
                y.setStatus(Status.BUSY);
                userService.updateUser(y);
            });
            request.getSession().removeAttribute("email");
            loggedUsers.remove(email.get());
        });
        request.getSession().removeAttribute("role");
        return "redirect:login";
    }
}
