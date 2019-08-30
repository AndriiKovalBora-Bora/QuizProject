/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.statistics.Statistics;
import model.entities.user.Status;
import model.entities.user.User;
import model.service.*;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * PlayerStatisticsPhase
 */
public class PlayerStatisticsPhaseTest {

    @Test
    public void execute() {
        PlayerStatisticsPhase playerStatisticsPhase =
                new PlayerStatisticsPhase(new UserService(), Constants.PLAYER_STATISTICS_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/view/error.jsp", playerStatisticsPhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("testEmail@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/view/error.jsp", playerStatisticsPhase.execute(request, response));

        Statistics activeGame = new Statistics();
        activeGame.setId(1);
        User player = new UserService().getUserByEmail("testEmail@gmail.com").get();
        activeGame.getPlayers().add(player);
        activeGame.setConfiguration(new Configuration());
        activeGames.add(activeGame);
        assertEquals("/WEB-INF/player/game/playerStatisticsPhase.jsp", playerStatisticsPhase.execute(request, response));

        when(request.getParameter("finish")).thenReturn("value");
        playerStatisticsPhase.setNextPhase(new PlayerPlayingGamePhase(new UserService(), new TimeService(), new HintService(), new StatisticsService(), new QuestionService(), Constants.PLAYER_GAME_PHASE));
        assertEquals("/WEB-INF/player/game/playerGamePhase.jsp", playerStatisticsPhase.execute(request, response));
        player.setStatus(Status.BUSY);
        new UserService().updateUser(player);
    }
}