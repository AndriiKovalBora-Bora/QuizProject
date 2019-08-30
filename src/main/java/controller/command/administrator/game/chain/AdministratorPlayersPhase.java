/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

import controller.command.Command;
import model.constants.Constants;
import model.entities.statistics.Statistics;
import model.entities.user.LocalizedUser;
import model.entities.user.User;
import model.entities.user.Status;
import model.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Processes AdministratorPlayersPhase
 */
public class AdministratorPlayersPhase extends AdministratorGamePhase implements Command {

    /**
     * User service
     */
    private UserService userService;

    /**
     * Creates AdministratorPlayersPhase class
     *
     * @param userService User service
     * @param phase       The number of current phase
     */
    AdministratorPlayersPhase(UserService userService, int phase) {
        super(phase);
        Logger.getLogger(this.getClass()).info("AdministratorPlayersPhase class constructor");
        this.userService = userService;
    }

    /**
     * Executes AdministratorPlayersPhase
     *
     * @param request  Http request
     * @param response Http response
     * @return JSP to be displayed
     */
    @Override
    @SuppressWarnings("unchecked")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(this.getClass()).info("AdministratorPlayersPhase class, execute method");
        request.getSession().setAttribute("phase", Constants.ADMINISTRATOR_PLAYERS_PHASE);
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        List<Statistics> activeGames = ((ArrayList<Statistics>) request.getSession().getServletContext().getAttribute("activeGames"));
        Optional<User> administrator = userService.getUserByEmail((String) request.getSession().getAttribute("email"));
        if (!administrator.isPresent()) {
            Logger.getLogger(this.getClass()).error("Administrator is not present");
            return "/WEB-INF/view/error.jsp";
        }

        Optional<Statistics> activeGame = activeGames
                .stream()
                .filter(x -> x.getAdministrator().equals(administrator.get()))
                .findAny();

        if (!activeGame.isPresent()) {
            Logger.getLogger(this.getClass()).error("Game is not present");
            return "/WEB-INF/view/error.jsp";
        }

        List<LocalizedUser> freePlayers = userService
                .getAllFreeUsers()
                .stream()
                .map(x -> x.getLocalizedUsers().get(locale))
                .collect(Collectors.toList());
        request.setAttribute("freePlayers", freePlayers);
        request.setAttribute("numberOfPlayers", activeGame.get().getConfiguration().getNumberOfPlayers());

        if ((request.getParameter("choosePlayers") != null)
                && (request.getParameterValues("gamePlayers").length == activeGame.get()
                .getConfiguration().getNumberOfPlayers())
                && (request.getParameterValues("gamePlayers") != null)) {
            List<User> chosenPlayers = new ArrayList<>();
            Arrays.stream(request.getParameterValues("gamePlayers"))
                    .forEach(x -> {
                        User player = userService.getUserById(x);
                        player.setStatus(Status.BUSY);
                        player.getStatistics().add(activeGame.get());
                        userService.updateUser(player);
                        Logger.getLogger(this.getClass()).info("User : " + player + " was updated in DB");
                        chosenPlayers.add(player);
                    });

            activeGame.get().setPlayers(chosenPlayers);
            Logger.getLogger(this.getClass()).info("Next phase : ADMINISTRATOR_GAME_PHASE");
            return nextPhase.executePhase(request, response, Constants.ADMINISTRATOR_GAME_PHASE);
        }

        return "/WEB-INF/administrator/game/administratorPlayersPhase.jsp";
    }
}
