/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.hint;

import model.entities.configuration.Configuration;
import model.entities.question.TypeOfQuestion;

import java.io.Serializable;
import java.util.*;

/**
 * Presents hint in certain language
 */
public class LocalizedHint implements Serializable {
    private Integer id;
    private TypeOfHint typeOfHint;
    private TypeOfQuestion typeOfQuestion;
    private List<Configuration> configurations = new ArrayList<>();
    private String hintFormulation;

    public LocalizedHint() {
    }

    public LocalizedHint(Integer id, TypeOfHint typeOfHint, TypeOfQuestion typeOfQuestion, List<Configuration> configurations, String hintFormulation) {
        this.id = id;
        this.typeOfHint = typeOfHint;
        this.typeOfQuestion = typeOfQuestion;
        this.configurations = configurations;
        this.hintFormulation = hintFormulation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeOfHint getTypeOfHint() {
        return typeOfHint;
    }

    public void setTypeOfHint(TypeOfHint typeOfHint) {
        this.typeOfHint = typeOfHint;
    }

    public TypeOfQuestion getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public String getHintFormulation() {
        return hintFormulation;
    }

    public void setHintFormulation(String hintFormulation) {
        this.hintFormulation = hintFormulation;
    }

    /**
     * Compares two localized hints
     *
     * @param o Object to be compared with current hint
     * @return True if localized hints are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalizedHint)) return false;
        LocalizedHint that = (LocalizedHint) o;
        return Objects.equals(getId(), that.getId()) &&
                getTypeOfHint() == that.getTypeOfHint() &&
                getTypeOfQuestion() == that.getTypeOfQuestion() &&
                Objects.equals(getHintFormulation(), that.getHintFormulation());
    }

    /**
     * Calculates hash code of the localized hint
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTypeOfHint(), getTypeOfQuestion(), getHintFormulation());
    }

    /**
     * Presents localized hint information in String type
     *
     * @return String representation of localized hint
     */
    @Override
    public String toString() {
        return "LocalizedHint{" +
                "hintFormulation='" + hintFormulation + '\'' +
                '}';
    }
}
