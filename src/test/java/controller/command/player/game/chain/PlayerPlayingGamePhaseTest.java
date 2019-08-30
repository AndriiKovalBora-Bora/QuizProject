/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.player.game.chain;

import model.constants.Constants;
import model.entities.Locales;
import model.entities.configuration.Configuration;
import model.entities.question.Question;
import model.entities.question.TypeOfQuestion;
import model.entities.statistics.Statistics;
import model.service.*;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests PlayerPlayingGamePhase
 */
public class PlayerPlayingGamePhaseTest {

    @Test
    public void execute() {
        PlayerPlayingGamePhase playerPlayingGamePhase =
                new PlayerPlayingGamePhase(new UserService(), new TimeService(), new HintService(), new StatisticsService(), new QuestionService(), Constants.PLAYER_GAME_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/view/error.jsp", playerPlayingGamePhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("andrew@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/player/game/playerGamePhase.jsp", playerPlayingGamePhase.execute(request, response));

        Statistics activeGame = new Statistics();
        activeGame.setId(1);
        activeGame.getPlayers().add(new UserService().getUserByEmail("andrew@gmail.com").get());
        Configuration configuration = new Configuration();
        configuration.setId(1);
        activeGame.setConfiguration(configuration);
        activeGames.add(activeGame);

        HashMap<Integer, ArrayList<ArrayList<String>>> chats = new HashMap<>();
        ArrayList<ArrayList<String>> chat = new ArrayList<>();
        chats.put(1, chat);
        when(request.getSession().getServletContext().getAttribute("chats")).thenReturn(chats);
        Question activeQuestion = new Question();
        activeQuestion.setTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES);
        activeGame.getQuestions().add(activeQuestion);
        activeGame.setPlayersScore(0);
        playerPlayingGamePhase.setNextPhase(new PlayerStatisticsPhase(new UserService(), Constants.PLAYER_STATISTICS_PHASE));
        assertEquals("/WEB-INF/player/game/playerStatisticsPhase.jsp", playerPlayingGamePhase.execute(request, response));
    }
}