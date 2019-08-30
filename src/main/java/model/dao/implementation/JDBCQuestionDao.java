/*
 * Copyright (C) 2019 Quiz Project
 */

package model.dao.implementation;

import model.dao.QuestionDao;
import model.dao.mapper.HintMapper;
import model.dao.mapper.QuestionMapper;
import model.dao.mapper.StatisticsMapper;
import model.entities.Locales;
import model.entities.hint.Hint;
import model.entities.question.Question;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements methods of QuestionDao interface
 */
public class JDBCQuestionDao implements QuestionDao {

    /**
     * Connection to the DB
     */
    private Connection connection;

    /**
     * Creates class
     *
     * @param connection Connection to the DB
     */
    JDBCQuestionDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Implements adding question to DB
     *
     * @param entity Entity to be stored in DB
     * @return Id of created record in DB
     */
    @Override
    public int create(Question entity) {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, create method");
        String query = "INSERT INTO question( " +
                "formulation_en, " +
                "formulation_ua, " +
                "answer_en, " +
                "answer_ua, " +
                "player_answer, " +
                "hint_formulation_en, " +
                "hint_formulation_ua, " +
                "choice_one_en, " +
                "choice_one_ua, " +
                "choice_two_en, " +
                "choice_two_ua, " +
                "choice_three_en, " +
                "choice_three_ua, " +
                "choice_four_en, " +
                "choice_four_ua, " +
                "type_of_question, " +
                "start_time, " +
                "end_time) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String queryGetId = "SELECT LAST_INSERT_ID()";

        try (Statement st = connection.createStatement();
             PreparedStatement ps = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getFormulation());
            ps.setString(2, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getFormulation());
            ps.setString(3, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getAnswer());
            ps.setString(4, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getAnswer());
            ps.setString(5, entity.getPlayerAnswer());
            ps.setString(6, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            ps.setString(7, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            ps.setString(8, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceOne());
            ps.setString(9, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceOne());
            ps.setString(10, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceTwo());
            ps.setString(11, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceTwo());
            ps.setString(12, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceThree());
            ps.setString(13, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceThree());
            ps.setString(14, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceFour());
            ps.setString(15, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceFour());
            ps.setString(16, entity.getTypeOfQuestion().name().toLowerCase());
            ps.setLong(17, entity.getStartTime());
            ps.setLong(18, entity.getEndTime());
            ps.executeUpdate();

            ResultSet rs = st.executeQuery(queryGetId);
            connection.commit();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, create error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Implements retrieving question from DB by Id
     *
     * @param id Id of the question
     * @return Question
     */
    @Override
    public Question findById(int id) {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, findById method");
        String query = "SELECT * FROM question " +
                "LEFT JOIN statistics ON question.id_statistics = statistics.id_statistics " +
                "WHERE question.id_question = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            QuestionMapper questionMapper = new QuestionMapper();
            StatisticsMapper statisticsMapper = new StatisticsMapper();

            if (!rs.next()) return null;
            Question question = questionMapper.extractFromResultSet(rs, "question");
            question.setStatistics(statisticsMapper.extractFromResultSet(rs, "statistics"));

            return question;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, findById error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves all questions from DB
     *
     * @return List of all questions
     */
    @Override
    public List<Question> findAll() {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, findAll method");
        Map<Integer, Question> questions = new HashMap<>();

        String query = "SELECT * FROM question " +
                "LEFT JOIN question_has_hint h ON question.id_question = h.id_question " +
                "LEFT JOIN hint h2 ON h.id_hint = h2.id_hint";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            QuestionMapper questionMapper = new QuestionMapper();
            HintMapper hintMapper = new HintMapper();

            while (rs.next()) {
                Question question = questionMapper.extractFromResultSet(rs, "question");
                question = questionMapper.makeUnique(questions, question);
                Hint hint = hintMapper.extractFromResultSet(rs, "h2");
                if (!question.getHints().contains(hint))
                    question.getHints().add(hint);
            }

            return new ArrayList<>(questions.values());
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, findAll error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves questions, which have not statistics
     *
     * @return List of Questions
     */
    @Override
    public List<Question> findAllFreeQuestion() {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, findAllFreeQuestion method");
        List<Question> questions = new ArrayList<>();

        String query = "SELECT * FROM question " +
                "WHERE id_statistics IS NULL";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            QuestionMapper questionMapper = new QuestionMapper();

            while (rs.next()) {
                questions.add(questionMapper.extractFromResultSet(rs, "question"));
            }

            return questions;
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, findAllFreeQuestion error");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates question in DB
     *
     * @param entity Question to be updated in DB
     */
    @Override
    public void update(Question entity) {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, update method");
        String queryQuestion = "UPDATE question SET " +
                "formulation_en = ?, " +
                "formulation_ua = ?, " +
                "answer_en = ?, " +
                "answer_ua = ?, " +
                "player_answer = ?, " +
                "hint_formulation_en = ?, " +
                "hint_formulation_ua = ?, " +
                "choice_one_en = ?, " +
                "choice_one_ua = ?, " +
                "choice_two_en = ?, " +
                "choice_two_ua = ?, " +
                "choice_three_en = ?, " +
                "choice_three_ua = ?, " +
                "choice_four_en = ?, " +
                "choice_four_ua = ?, " +
                "type_of_question = ?, " +
                "id_statistics = ?, " +
                "start_time = ?," +
                "end_time = ? " +
                "WHERE question.id_question = ?";

        String queryQuestionHasHint = "INSERT IGNORE INTO question_has_hint(id_question, id_hint) " +
                "VALUES(?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(queryQuestion);
             PreparedStatement ps1 = connection.prepareStatement(queryQuestionHasHint)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            ps.setString(1, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getFormulation());
            ps.setString(2, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getFormulation());
            ps.setString(3, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getAnswer());
            ps.setString(4, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getAnswer());
            ps.setString(5, entity.getPlayerAnswer());
            ps.setString(6, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getHintFormulation());
            ps.setString(7, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getHintFormulation());
            ps.setString(8, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceOne());
            ps.setString(9, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceOne());
            ps.setString(10, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceTwo());
            ps.setString(11, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceTwo());
            ps.setString(12, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceThree());
            ps.setString(13, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceThree());
            ps.setString(14, entity.getLocaleLocalizedQuestions().get(Locales.ENGLISH.getLocale()).getChoiceFour());
            ps.setString(15, entity.getLocaleLocalizedQuestions().get(Locales.UKRAINIAN.getLocale()).getChoiceFour());
            ps.setString(16, entity.getTypeOfQuestion().name().toLowerCase());
            ps.setInt(17, entity.getStatistics().getId());
            ps.setLong(18, entity.getStartTime());
            ps.setLong(19, entity.getEndTime());
            ps.setInt(20, entity.getId());
            ps.executeUpdate();

            entity.getHints().forEach(x -> {
                try {
                    ps1.setInt(1, entity.getId());
                    ps1.setInt(2, x.getId());
                    ps1.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, update error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deletes question from DB by id
     *
     * @param id Id of the Question to be deleted
     */
    @Override
    public void delete(int id) {
        Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, delete method");
        String queryQuestionHasHints = "DELETE FROM question_has_hint WHERE id_question = ? ";
        String queryQuestion = "DELETE FROM question WHERE id_question = ? ";

        try (PreparedStatement ps = connection.prepareStatement(queryQuestionHasHints);
             PreparedStatement ps1 = connection.prepareStatement(queryQuestion)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ps.setInt(1, id);
            ps.executeUpdate();

            ps1.setInt(1, id);
            ps1.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, delete error");
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Closes connection to DB
     */
    @Override
    public void close() {
        try {
            Logger.getLogger(this.getClass()).info("JDBCQuestionDao class, close method");
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(this.getClass()).error("JDBCQuestionDao class, close error");
            throw new RuntimeException(e);
        }
    }
}
