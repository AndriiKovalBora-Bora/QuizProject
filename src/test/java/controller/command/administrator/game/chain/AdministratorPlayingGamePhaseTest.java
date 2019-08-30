/*
 * Copyright (C) 2019 Quiz Project
 */

package controller.command.administrator.game.chain;

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
 * Tests AdministratorPlayingGamePhase
 */
public class AdministratorPlayingGamePhaseTest {

    @Test
    public void execute() {
        AdministratorPlayingGamePhase administratorPlayingGamePhase =
                new AdministratorPlayingGamePhase(new QuestionService(), new UserService(), new TimeService(), new HintService(), new StatisticsService(), Constants.ADMINISTRATOR_GAME_PHASE);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getServletContext()).thenReturn(mock(ServletContext.class));

        request.getSession().setAttribute("locale", Locales.ENGLISH.getLocale());
        assertEquals("/WEB-INF/view/error.jsp", administratorPlayingGamePhase.execute(request, response));

        when(request.getSession().getAttribute("email")).thenReturn("ira@gmail.com");
        ArrayList<Statistics> activeGames = new ArrayList<>();
        when(request.getSession().getServletContext().getAttribute("activeGames")).thenReturn(activeGames);
        assertEquals("/WEB-INF/administrator/game/administratorGamePhase.jsp", administratorPlayingGamePhase.execute(request, response));

        Statistics activeGame = new Statistics();
        activeGame.setId(1);
        activeGame.setAdministrator(new UserService().getUserByEmail("ira@gmail.com").get());
        activeGame.setConfiguration(new Configuration());
        activeGames.add(activeGame);

        HashMap<Integer, ArrayList<ArrayList<String>>> chats = new HashMap<>();
        ArrayList<ArrayList<String>> chat = new ArrayList<>();
        chats.put(1, chat);
        when(request.getSession().getServletContext().getAttribute("chats")).thenReturn(chats);
        when(request.getParameter("choosePlayers")).thenReturn("value");
        assertEquals("/WEB-INF/administrator/game/administratorGamePhase.jsp", administratorPlayingGamePhase.execute(request, response));

        Question activeQuestion = new Question();
        activeQuestion.setTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES);
        activeGame.getQuestions().add(activeQuestion);
        assertEquals("/WEB-INF/administrator/game/administratorGamePhase.jsp", administratorPlayingGamePhase.execute(request, response));

        when(request.getParameter("assessAnswer")).thenReturn("value");
        activeGame.setPlayersScore(0);
        administratorPlayingGamePhase.setNextPhase(new AdministratorStatisticsPhase(Constants.ADMINISTRATOR_STATISTICS_PHASE));
        assertEquals("/WEB-INF/administrator/game/administratorStatisticsPhase.jsp", administratorPlayingGamePhase.execute(request, response));
    }
}