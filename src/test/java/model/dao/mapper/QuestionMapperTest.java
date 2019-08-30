/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.dao.implementation.ConnectionPoolHolder;
import model.entities.Locales;
import model.entities.question.Question;
import model.entities.question.TypeOfQuestion;
import model.entities.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests QuestionMapper
 */
public class QuestionMapperTest {
    private static QuestionMapper questionMapper;

    @BeforeClass
    public static void setUp() {
        questionMapper = new QuestionMapper();
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
        final String query = "SELECT * FROM question " +
                "WHERE question.id_question = 1";
        try(Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) fail();
            Question question = questionMapper.extractFromResultSet(rs, "question");
            assertEquals("two plus two ?", question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getFormulation());
            assertEquals("four", question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getAnswer());
            assertEquals("about four", question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            assertEquals("два плюс два ?", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getFormulation());
            assertEquals("чотири", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getAnswer());
            assertEquals("приблизно чотири", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            assertEquals("one", question.getPlayerAnswer());
            assertEquals(TypeOfQuestion.WITHOUT_CHOICES, question.getTypeOfQuestion());
            assertEquals(1566234705929L, question.getStartTime());
            assertEquals(1566234709929L, question.getEndTime());
        }
    }

    @Test
    public void makeUnique() {
        Map<Integer, Question> questions = new HashMap<>();
        Question question1 = new Question();
        question1.setId(1);

        Question question2 = new Question();
        question2.setId(2);

        questions.put(question1.getId(), question1);

        assertEquals(question1, questionMapper.makeUnique(questions, question1));
        assertEquals(question2, questionMapper.makeUnique(questions, question2));
    }
}