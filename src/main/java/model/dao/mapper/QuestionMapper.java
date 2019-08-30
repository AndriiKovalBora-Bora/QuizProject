/*
 * Copyright (C) 2019 Quiz Project
 */


package model.dao.mapper;

import model.entities.Locales;
import model.entities.question.LocalizedQuestion;
import model.entities.question.Question;
import model.entities.question.TypeOfQuestion;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Provides methods for extracting Question data from ResultSet
 */
public class QuestionMapper implements ObjectMapper<Question> {

    /**
     * Extracts data from ResultSet and creates Question
     *
     * @param rs        ResultSet to be processed
     * @param tableName Name of the table
     * @return Question entity
     * @throws SQLException if error was occurred
     */
    @Override
    public Question extractFromResultSet(ResultSet rs, String tableName) throws SQLException {
        Logger.getLogger(this.getClass()).info("QuestionMapper class, extractFromResultSet method");
        if (rs.getString(tableName + ".id_question") == null) {
            Logger.getLogger(this.getClass()).info("id_question is null");
            return null;
        }

        Question question = new Question();
        question.setId(rs.getInt(tableName + ".id_question"));
        question.setPlayerAnswer(rs.getString(tableName + ".player_answer"));
        question.setTypeOfQuestion(TypeOfQuestion.valueOf(rs.getString(tableName + ".type_of_question").toUpperCase()));
        question.setStartTime(rs.getLong(tableName + ".start_time"));
        question.setEndTime(rs.getLong(tableName + ".end_time"));

        LocalizedQuestion localizedQuestionEN = new LocalizedQuestion();
        localizedQuestionEN.setId(question.getId());
        localizedQuestionEN.setPlayerAnswer(question.getPlayerAnswer());
        localizedQuestionEN.setTypeOfQuestion(question.getTypeOfQuestion());
        localizedQuestionEN.setStartTime(question.getStartTime());
        localizedQuestionEN.setEndTime(question.getEndTime());
        localizedQuestionEN.setFormulation(rs.getString(tableName + ".formulation_en"));
        localizedQuestionEN.setAnswer(rs.getString(tableName + ".answer_en"));
        localizedQuestionEN.setHintFormulation(rs.getString(tableName + ".hint_formulation_en"));
        localizedQuestionEN.setChoiceOne(rs.getString(tableName + ".choice_one_en"));
        localizedQuestionEN.setChoiceTwo(rs.getString(tableName + ".choice_two_en"));
        localizedQuestionEN.setChoiceThree(rs.getString(tableName + ".choice_three_en"));
        localizedQuestionEN.setChoiceFour(rs.getString(tableName + ".choice_four_en"));

        LocalizedQuestion localizedQuestionUA = new LocalizedQuestion();
        localizedQuestionUA.setId(question.getId());
        localizedQuestionUA.setPlayerAnswer(question.getPlayerAnswer());
        localizedQuestionUA.setTypeOfQuestion(question.getTypeOfQuestion());
        localizedQuestionUA.setStartTime(question.getStartTime());
        localizedQuestionUA.setEndTime(question.getEndTime());
        localizedQuestionUA.setFormulation(rs.getString(tableName + ".formulation_ua"));
        localizedQuestionUA.setAnswer(rs.getString(tableName + ".answer_ua"));
        localizedQuestionUA.setHintFormulation(rs.getString(tableName + ".hint_formulation_ua"));
        localizedQuestionUA.setChoiceOne(rs.getString(tableName + ".choice_one_ua"));
        localizedQuestionUA.setChoiceTwo(rs.getString(tableName + ".choice_two_ua"));
        localizedQuestionUA.setChoiceThree(rs.getString(tableName + ".choice_three_ua"));
        localizedQuestionUA.setChoiceFour(rs.getString(tableName + ".choice_four_ua"));

        question.getLocaleLocalizedQuestions().put(Locales.ENGLISH.getLocale(), localizedQuestionEN);
        question.getLocaleLocalizedQuestions().put(Locales.UKRAINIAN.getLocale(), localizedQuestionUA);

        return question;
    }

    /**
     * Checks if Question is unique or not
     *
     * @param cache  Map of questions, which were extracted earlier
     * @param entity Current question
     * @return Unique question
     */
    @Override
    public Question makeUnique(Map<Integer, Question> cache, Question entity) {
        Logger.getLogger(this.getClass()).info("QuestionMapper class, makeUnique method");
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
