/*
 * Copyright (C) 2019 Quiz Project
 */

package controller;

import controller.command.*;
import controller.command.administrator.AdministratorConfiguration;
import controller.command.administrator.AdministratorHome;
import controller.command.administrator.AdministratorProfile;
import controller.command.administrator.game.chain.AdministratorGame;
import controller.command.player.PlayerHome;
import controller.command.player.PlayerProfile;
import controller.command.player.game.chain.PlayerGame;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Provides method for processing requests and responses
 */
public class Servlet extends HttpServlet {

    /**
     * Map with Url patterns and actions to be executed, according to the pattern
     */
    private HashMap<String, Command> commands = new HashMap<>();

    /**
     * Initializes servlet
     */
    @Override
    public void init() {
        Logger.getLogger(this.getClass()).info("Servlet initialization");

        commands.put("index", new Index(new StatisticsService()));
        commands.put("login", new Login(new UserService()));
        commands.put("logout", new Logout(new UserService()));
        commands.put("registration", new Registration(new UserService()));

        commands.put("playerHome", new PlayerHome(new StatisticsService()));
        commands.put("playerProfile", new PlayerProfile(new StatisticsService(), new UserService()));
        commands.put("playerGame", new PlayerGame());

        commands.put("administratorHome", new AdministratorHome(new StatisticsService()));
        commands.put("administratorProfile", new AdministratorProfile(new StatisticsService(), new UserService()));
        commands.put("administratorGame", new AdministratorGame());
        commands.put("administratorConfiguration", new AdministratorConfiguration(new ConfigurationService(), new HintService()));
    }

    /**
     * Executes doGet method
     *
     * @param req  Http request
     * @param resp Http response
     * @throws ServletException If error was occurred
     * @throws IOException      If error was occurred
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.getLogger(this.getClass()).info("Servlet doGet method");
        processRequest(req, resp);
    }

    /**
     * Executes doPost method
     *
     * @param req  Http request
     * @param resp Http response
     * @throws ServletException If error was occurred
     * @throws IOException      If error was occurred
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.getLogger(this.getClass()).info("Servlet doGet method");
        processRequest(req, resp);

    }

    /**
     * Process request, makes corresponding url pattern and executes its appropriate command
     *
     * @param req  Http request
     * @param resp Http response
     * @throws IOException      If error was occurred
     * @throws ServletException If error was occurred
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Logger.getLogger(this.getClass()).info("Servlet processRequest method");
        String path = req.getRequestURI();
        Logger.getLogger(this.getClass()).info("Requested URI : " + path);
        path = path.replaceAll(".*/app/", "");
        Logger.getLogger(this.getClass()).info("Command path : " + path);
        Command command = commands.getOrDefault(path, (r, q) -> "/WEB-INF/view/notFound.jsp");

        String page = command.execute(req, resp);
        Logger.getLogger(this.getClass()).info("Page : " + page);
        if (page.contains("redirect")) {
            resp.sendRedirect(page.replace("redirect:", ""));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }
}
