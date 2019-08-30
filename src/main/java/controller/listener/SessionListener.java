/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.listener;

import model.entities.Locales;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import model.entities.user.Status;
import model.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

/**
 * Realizes session listener
 */
public class SessionListener implements HttpSessionListener {

    /**
     * Initializes session
     * @param se Http session even
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Logger.getLogger(this.getClass()).info("SessionListener create");
        se.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
    }

    /**
     * Destroys session
     * @param se Http session even
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent se) {
        Logger.getLogger(this.getClass()).info("SessionListener destroy");
        HashSet<String> loggedUsers = (HashSet<String>) se.getSession().getServletContext().getAttribute("loggedUsers");

        String email = (String) se.getSession().getAttribute("email");
        Optional<User> user = new UserService().getUserByEmail(email);
        if (!user.isPresent())
            return;

        user.get().setStatus(Status.BUSY);
        new UserService().updateUser(user.get());
        loggedUsers.remove(email);

        List<Statistics> activeGames = ((ArrayList<Statistics>) se.getSession().getServletContext().getAttribute("activeGames"));
        HashMap<Integer, ArrayList<String>> chats = (HashMap<Integer, ArrayList<String>>) se.getSession().getServletContext().getAttribute("chats");
        Optional<Statistics> activeGame = activeGames.stream().filter(x -> x.getAdministrator().equals(user.get()))
                .findAny();

        activeGame.ifPresent(x -> {
            activeGames.remove(x);
            chats.remove(x.getId());
        });
    }
}
