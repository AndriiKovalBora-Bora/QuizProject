/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command;

import model.entities.user.User;
import model.entities.user.Role;
import model.entities.user.Status;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

/**
 * Processes login command
 */
public class Login implements Command {

    /**
     * Redirect pages
     */
    private Map<String, String> pages = new HashMap<>();

    /**
     * User service
     */
    private UserService userService;

    /**
     * Creates Login class
     *
     * @param userService User service
     */
    public Login(UserService userService) {
        this.userService = userService;

        Logger.getLogger(this.getClass()).info("Login class constructor");
        pages.put("login", "/login.jsp");
        pages.put("PLAYER", "redirect:playerProfile");
        pages.put("ADMINISTRATOR", "redirect:administratorProfile");
    }

    /**
     * Executes login command
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("Login class, execute method");
        if (request.getParameter("loginUser") == null) {
            Logger.getLogger(this.getClass()).info("Login user");
            return "/login.jsp";
        }

        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user = userService.getUserByEmail(email);
        if (loggedUsers.stream().anyMatch(email::equals)){
            Logger.getLogger(this.getClass()).info("Cannot login");
            request.setAttribute("cannotLogin", true);
            return "/login.jsp";
        }
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            Logger.getLogger(this.getClass()).info("User is present");
            request.getSession().setAttribute("email", email);
            request.getSession().setAttribute("role", user.get().getRole().name().toLowerCase());
            loggedUsers.add(email);
            if (user.get().getRole() != Role.ADMINISTRATOR)
                user.get().setStatus(Status.FREE);
            userService.updateUser(user.get());
            String redirectPage = (String) request.getSession().getAttribute("redirectPage");
            if (redirectPage != null) {
                request.getSession().removeAttribute("redirectPage");
                Logger.getLogger(this.getClass()).info("Redirect page after login : " + redirectPage);
                return "redirect:" + redirectPage;
            }
            return pages.getOrDefault(user.get().getRole().name(), pages.get("login"));
        } else {
            Logger.getLogger(this.getClass()).info("Cannot login");
            request.setAttribute("cannotLogin", true);
            return "/login.jsp";
        }
    }
}
