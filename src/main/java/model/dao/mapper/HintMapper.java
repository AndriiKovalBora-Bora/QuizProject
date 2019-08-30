/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.mapper;

import model.entities.Locales;
import model.entities.hint.Hint;
import model.entities.hint.LocalizedHint;
import model.entities.hint.TypeOfHint;
import model.entities.question.TypeOfQuestion;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides methods for extracting Hint data from ResultSet
 */
public class HintMapper implements ObjectMapper<Hint> {

    /**
     * Extracts data from ResultSet and creates Hint
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return Hint entity
     * @throws SQLException if error was occurred
     */
    @Override
    public Hint extractFromResultSet(ResultSet rs, String tableName) throws SQLException {
        Logger.getLogger(this.getClass()).info("HintMapper class, extractFromResultSet method");
        if (rs.getString(tableName + ".id_hint") == null) {
            Logger.getLogger(this.getClass()).info("id_hint is null");
            return null;
        }

        Hint hint = new Hint();
        hint.setId(rs.getInt(tableName + ".id_hint"));
        hint.setTypeOfHint(TypeOfHint.valueOf(rs.getString(tableName + ".type_of_hint").toUpperCase()));
        hint.setTypeOfQuestion(TypeOfQuestion.valueOf(rs.getString(tableName + ".type_of_question").toUpperCase()));

        LocalizedHint localizedHintEN = new LocalizedHint();
        localizedHintEN.setId(hint.getId());
        localizedHintEN.setTypeOfHint(hint.getTypeOfHint());
        localizedHintEN.setTypeOfQuestion(hint.getTypeOfQuestion());
        localizedHintEN.setHintFormulation(rs.getString(tableName + ".hint_formulation_en"));

        LocalizedHint localizedHintUA = new LocalizedHint();
        localizedHintUA.setId(hint.getId());
        localizedHintUA.setTypeOfHint(hint.getTypeOfHint());
        localizedHintUA.setTypeOfQuestion(hint.getTypeOfQuestion());
        localizedHintUA.setHintFormulation(rs.getString(tableName + ".hint_formulation_ua"));

        hint.getLocalizedHints().put(Locales.ENGLISH.getLocale(), localizedHintEN);
        hint.getLocalizedHints().put(Locales.UKRAINIAN.getLocale(), localizedHintUA);
        return hint;
    }

    /**
     * Checks if Hint is unique or not
     *
     * @param cache  Map of hints, which were extracted earlier
     * @param entity Current hint
     * @return Unique hint
     */
    @Override
    public Hint makeUnique(Map<Integer, Hint> cache, Hint entity) {
        Logger.getLogger(this.getClass()).info("HintMapper class, makeUnique method");
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
