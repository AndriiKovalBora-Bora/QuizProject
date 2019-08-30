/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.dao.implementation.ConnectionPoolHolder;
import model.entities.Locales;
import model.entities.hint.Hint;
import model.entities.hint.TypeOfHint;
import model.entities.question.TypeOfQuestion;
import model.entities.user.Role;
import model.entities.user.Status;
import model.entities.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test HintMapper
 */
public class HintMapperTest {
    private static HintMapper hintMapper;

    @BeforeClass
    public static void setUp() {
        hintMapper = new HintMapper();
    }

    @Test
    public void extractFromResultSet() throws SQLException {
        Connection connection = ConnectionPoolHolder.getDataSource().getConnection();
        final String query = "SELECT * FROM hint " +
                "WHERE hint.id_hint = 1";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            if (!rs.next()) fail();
            Hint hint = hintMapper.extractFromResultSet(rs, "hint");
            assertEquals(TypeOfHint.ADD_TIME, hint.getTypeOfHint());
            assertEquals("Add time", hint.getLocalizedHints().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            assertEquals("Додати час", hint.getLocalizedHints().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            assertEquals(TypeOfQuestion.WITHOUT_CHOICES, hint.getTypeOfQuestion());
            connection.close();
        }
    }

    @Test
    public void makeUnique() {
        Map<Integer, Hint> hints = new HashMap<>();
        Hint hint1 = new Hint();
        hint1.setId(1);

        Hint hint2 = new Hint();
        hint2.setId(2);

        hints.put(hint1.getId(), hint1);

        assertEquals(hint1, hintMapper.makeUnique(hints, hint1));
        assertEquals(hint2, hintMapper.makeUnique(hints, hint2));
    }
}