/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.question;

import model.entities.statistics.Statistics;
import model.entities.hint.Hint;

import java.io.Serializable;
import java.util.*;

/**
 * Presents Question
 */
public class Question implements Serializable {
    private Integer id;
    private String playerAnswer;
    private Statistics statistics;
    private TypeOfQuestion typeOfQuestion;
    private long startTime;
    private long endTime;
    private List<Hint> hints = new ArrayList<>();
    private Map<Locale, LocalizedQuestion> localeLocalizedQuestions = new HashMap<>();

    public Question() {
    }

    public Question(Integer id, String playerAnswer, Statistics statistics, TypeOfQuestion typeOfQuestion, long startTime, long endTime, List<Hint> hints, Map<Locale, LocalizedQuestion> localeLocalizedQuestions) {
        this.id = id;
        this.playerAnswer = playerAnswer;
        this.statistics = statistics;
        this.typeOfQuestion = typeOfQuestion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hints = hints;
        this.localeLocalizedQuestions = localeLocalizedQuestions;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        localeLocalizedQuestions.forEach((key, value) -> value.setId(id));
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public void setPlayerAnswer(String playerAnswer) {
        this.playerAnswer = playerAnswer;
        localeLocalizedQuestions.forEach((key, value) -> value.setPlayerAnswer(playerAnswer));
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
        localeLocalizedQuestions.forEach((key, value) -> value.setStatistics(statistics));
    }

    public TypeOfQuestion getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
        localeLocalizedQuestions.forEach((key, value) -> value.setTypeOfQuestion(typeOfQuestion));
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
        localeLocalizedQuestions.forEach((key, value) -> value.setStartTime(startTime));
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        localeLocalizedQuestions.forEach((key, value) -> value.setEndTime(endTime));
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
        localeLocalizedQuestions.forEach((key, value) -> value.setHints(hints));
    }

    public Map<Locale, LocalizedQuestion> getLocaleLocalizedQuestions() {
        return localeLocalizedQuestions;
    }

    public void setLocaleLocalizedQuestions(Map<Locale, LocalizedQuestion> localeLocalizedQuestions) {
        this.localeLocalizedQuestions = localeLocalizedQuestions;
    }

    /**
     * Compares two questions
     *
     * @param o Object to be compared with current question
     * @return True if users are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return getStartTime() == question.getStartTime() &&
                getEndTime() == question.getEndTime() &&
                Objects.equals(getId(), question.getId()) &&
                Objects.equals(getPlayerAnswer(), question.getPlayerAnswer()) &&
                getTypeOfQuestion() == question.getTypeOfQuestion() &&
                Objects.equals(getLocaleLocalizedQuestions(), question.getLocaleLocalizedQuestions());
    }

    /**
     * Calculates hash code of the question
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayerAnswer(), getTypeOfQuestion(), getStartTime(), getEndTime(), getLocaleLocalizedQuestions());
    }

    /**
     * Presents question information in String type
     *
     * @return String representation of question
     */
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", playerAnswer='" + playerAnswer + '\'' +
                ", statisticsId=" + statistics.getId() +
                ", typeOfQuestion=" + typeOfQuestion +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", hints={" + hints.stream().map(x -> String.valueOf(x.getId())).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                ", localeLocalizedQuestions={" + localeLocalizedQuestions.entrySet().stream().map(x -> x.getKey().getCountry() + " : " + x.getValue()).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                '}';
    }
}
