/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.question;

import model.entities.hint.Hint;
import model.entities.statistics.Statistics;

import java.io.Serializable;
import java.util.*;

/**
 * Presents question in certain language
 */
public class LocalizedQuestion implements Serializable {
    private Integer id;
    private String playerAnswer;
    private Statistics statistics;
    private TypeOfQuestion typeOfQuestion;
    private long startTime;
    private long endTime;
    private List<Hint> hints = new ArrayList<>();
    private String formulation;
    private String answer;
    private String hintFormulation;
    private String choiceOne;
    private String choiceTwo;
    private String choiceThree;
    private String choiceFour;

    public LocalizedQuestion() {
    }

    public LocalizedQuestion(Integer id, String playerAnswer, Statistics statistics, TypeOfQuestion typeOfQuestion, long startTime, long endTime, List<Hint> hints, String formulation, String answer, String hintFormulation, String choiceOne, String choiceTwo, String choiceThree, String choiceFour) {
        this.id = id;
        this.playerAnswer = playerAnswer;
        this.statistics = statistics;
        this.typeOfQuestion = typeOfQuestion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hints = hints;
        this.formulation = formulation;
        this.answer = answer;
        this.hintFormulation = hintFormulation;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
        this.choiceThree = choiceThree;
        this.choiceFour = choiceFour;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayerAnswer() {
        return playerAnswer;
    }

    public void setPlayerAnswer(String playerAnswer) {
        this.playerAnswer = playerAnswer;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public TypeOfQuestion getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<Hint> getHints() {
        return hints;
    }

    public void setHints(List<Hint> hints) {
        this.hints = hints;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHintFormulation() {
        return hintFormulation;
    }

    public void setHintFormulation(String hintFormulation) {
        this.hintFormulation = hintFormulation;
    }

    public String getChoiceOne() {
        return choiceOne;
    }

    public void setChoiceOne(String choiceOne) {
        this.choiceOne = choiceOne;
    }

    public String getChoiceTwo() {
        return choiceTwo;
    }

    public void setChoiceTwo(String choiceTwo) {
        this.choiceTwo = choiceTwo;
    }

    public String getChoiceThree() {
        return choiceThree;
    }

    public void setChoiceThree(String choiceThree) {
        this.choiceThree = choiceThree;
    }

    public String getChoiceFour() {
        return choiceFour;
    }

    public void setChoiceFour(String choiceFour) {
        this.choiceFour = choiceFour;
    }

    /**
     * Compares two localized questions
     *
     * @param o Object to be compared with current question
     * @return True if localized questions are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalizedQuestion)) return false;
        LocalizedQuestion that = (LocalizedQuestion) o;
        return getStartTime() == that.getStartTime() &&
                getEndTime() == that.getEndTime() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPlayerAnswer(), that.getPlayerAnswer()) &&
                getTypeOfQuestion() == that.getTypeOfQuestion() &&
                Objects.equals(getFormulation(), that.getFormulation()) &&
                Objects.equals(getAnswer(), that.getAnswer()) &&
                Objects.equals(getHintFormulation(), that.getHintFormulation()) &&
                Objects.equals(getChoiceOne(), that.getChoiceOne()) &&
                Objects.equals(getChoiceTwo(), that.getChoiceTwo()) &&
                Objects.equals(getChoiceThree(), that.getChoiceThree()) &&
                Objects.equals(getChoiceFour(), that.getChoiceFour());
    }

    /**
     * Calculates hash code of the localized question
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayerAnswer(), getTypeOfQuestion(), getStartTime(), getEndTime(), getFormulation(), getAnswer(), getHintFormulation(), getChoiceOne(), getChoiceTwo(), getChoiceThree(), getChoiceFour());
    }


    /**
     * Presents localized question information in String type
     *
     * @return String representation of localized question
     */
    @Override
    public String toString() {
        return "LocalizedQuestion{" +
                "formulation='" + formulation + '\'' +
                ", answer='" + answer + '\'' +
                ", hintFormulation='" + hintFormulation + '\'' +
                ", choiceOne='" + choiceOne + '\'' +
                ", choiceTwo='" + choiceTwo + '\'' +
                ", choiceThree='" + choiceThree + '\'' +
                ", choiceFour='" + choiceFour + '\'' +
                '}';
    }
}
