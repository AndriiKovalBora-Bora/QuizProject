/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.DaoFactory;
import model.dao.HintDao;
import model.entities.Locales;
import model.entities.hint.Hint;
import model.entities.hint.LocalizedHint;
import model.entities.hint.TypeOfHint;
import model.entities.question.TypeOfQuestion;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests JDBCHintDao
 */
public class JDBCHintDaoTest {
    private static HintDao hintDao;
    private static Hint testHint;

    @BeforeClass
    public static void setUp() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        hintDao = daoFactory.createHintDao();

        testHint = new Hint();
        LocalizedHint localizedHintEN = new LocalizedHint();
        localizedHintEN.setHintFormulation("testHint");

        LocalizedHint localizedHintUA = new LocalizedHint();
        localizedHintUA.setHintFormulation("тестПідказка");

        testHint.getLocalizedHints().put(Locales.ENGLISH.getLocale(), localizedHintEN);
        testHint.getLocalizedHints().put(Locales.UKRAINIAN.getLocale(), localizedHintUA);

        testHint.setTypeOfHint(TypeOfHint.ADD_TIME);
        testHint.setTypeOfQuestion(TypeOfQuestion.WITH_CHOICES);
    }

    @AfterClass
    public static void tearDown() {
        hintDao.close();
    }

    @Test
    public void create() {
        int id = hintDao.create(testHint);
        testHint.setId(id);
        Assert.assertEquals(testHint, hintDao.findById(id));
        hintDao.delete(id);
    }

    @Test
    public void findById() {
        Hint hint = hintDao.findById(1);
        assertEquals(TypeOfHint.ADD_TIME, hint.getTypeOfHint());
        assertEquals(TypeOfQuestion.WITHOUT_CHOICES, hint.getTypeOfQuestion());
        assertEquals("Add time", hint.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getHintFormulation());
        assertEquals("Додати час", hint.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
    }

    @Test
    public void findAll() {
        int id = hintDao.create(testHint);
        List<Hint> hints = hintDao.findAll();
        assertTrue(hints.size() > 0);
        hintDao.delete(id);
    }

    @Test
    public void findAllHintsByTypeOfQuestion() {
        int id = hintDao.create(testHint);
        List<Hint> hints = hintDao.findAllHintsByTypeOfQuestion(testHint.getTypeOfQuestion());
        assertTrue(hints.size() > 0);
        hintDao.delete(id);
    }

    @Test
    public void update() {
        int id = hintDao.create(testHint);

        testHint.setId(id);
        testHint.getLocalizedHints().get(Locales.ENGLISH.getLocale()).setHintFormulation("newFormulation");
        testHint.setTypeOfQuestion(TypeOfQuestion.WITHOUT_CHOICES);

        hintDao.update(testHint);
        Hint updatedHint = hintDao.findById(id);
        assertEquals(testHint.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getTypeOfQuestion(), updatedHint.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getTypeOfQuestion());
        assertEquals(testHint.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getTypeOfQuestion(), updatedHint.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getTypeOfQuestion());
        assertEquals(testHint.getTypeOfHint(), updatedHint.getTypeOfHint());
        assertEquals(testHint.getTypeOfQuestion(), updatedHint.getTypeOfQuestion());
        hintDao.delete(id);
    }

    @Test
    public void delete() {
        int id = hintDao.create(testHint);
        hintDao.delete(id);
        assertNull(hintDao.findById(id));
    }
}