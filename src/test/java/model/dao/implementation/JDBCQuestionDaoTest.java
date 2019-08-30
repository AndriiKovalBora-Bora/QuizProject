/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.DaoFactory;
import model.dao.QuestionDao;
import model.entities.Locales;
import model.entities.question.LocalizedQuestion;
import model.entities.question.Question;
import model.entities.question.TypeOfQuestion;
import model.entities.statistics.Statistics;
import model.entities.user.User;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests JDBCQuestionDao
 */
public class JDBCQuestionDaoTest {
    private static QuestionDao questionDao;
    private static Question testQuestion;

    @BeforeClass
    public static void setUp() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        questionDao = daoFactory.createQuestionDao();

        testQuestion = new Question();
        LocalizedQuestion localizedQuestionEN = new LocalizedQuestion();
        localizedQuestionEN.setFormulation("testFormulation");
        localizedQuestionEN.setAnswer("testAnswer");
        localizedQuestionEN.setHintFormulation("testHint");

        LocalizedQuestion localizedQuestionUA = new LocalizedQuestion();
        localizedQuestionUA.setFormulation("тестФормулювання");
        localizedQuestionUA.setAnswer("тестВідповідь");
        localizedQuestionUA.setHintFormulation("тестПідказка");

        testQuestion.getLocaleLocalizedQuestions().put(Locales.ENGLISH.getLocale(), localizedQuestionEN);
        testQuestion.getLocaleLocalizedQuestions().put(Locales.UKRAINIAN.getLocale(), localizedQuestionUA);

        testQuestion.setTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES);
    }

    @AfterClass
    public static void tearDown() {
        questionDao.close();
    }

    @Test
    public void create() {
        int id = questionDao.create(testQuestion);
        testQuestion.setId(id);
        assertEquals(testQuestion, questionDao.findById(id));
        questionDao.delete(id);
    }

    @Test
    public void findById() {
        Question question = questionDao.findById(1);
        assertEquals("two plus two ?", question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getFormulation());
        assertEquals("four",question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getAnswer());
        assertEquals("about four", question.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getHintFormulation());
        assertEquals("два плюс два ?", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getFormulation());
        assertEquals("чотири", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getAnswer());
        assertEquals("приблизно чотири", question.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
        assertEquals("one", question.getPlayerAnswer());
        assertEquals(TypeOfQuestion.WITHOUT_CHOICES, question.getTypeOfQuestion());
    }

    @Test
    public void findAll() {
        int id = questionDao.create(testQuestion);
        List<Question> questions = questionDao.findAll();
        assertTrue(questions.size() > 0);
        questionDao.delete(id);
    }

    @Test
    public void findAllFreeQuestion() {
        testQuestion.setStatistics(new Statistics());
        testQuestion.getStatistics().setId(null);
        int id = questionDao.create(testQuestion);
        List<Question> questions = questionDao.findAll();
        assertTrue(questions.size() > 0);
        questionDao.delete(id);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        int id = questionDao.create(testQuestion);
        questionDao.delete(id);
        assertNull(questionDao.findById(id));
    }
}