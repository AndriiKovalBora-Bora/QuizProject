/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.hint;

import model.entities.question.TypeOfQuestion;
import model.entities.configuration.Configuration;

import java.io.Serializable;
import java.util.*;

/**
 * Presents Hint
 */
public class Hint implements Serializable {
    private Integer id;
    private TypeOfHint typeOfHint;
    private TypeOfQuestion typeOfQuestion;
    private List<Configuration> configurations = new ArrayList<>();
    private Map<Locale, LocalizedHint> localizedHints = new HashMap<>();

    public Hint() {
    }

    public Hint(Integer id, TypeOfHint typeOfHint, TypeOfQuestion typeOfQuestion, List<Configuration> configurations, Map<Locale, LocalizedHint> localizedHints) {
        this.id = id;
        this.typeOfHint = typeOfHint;
        this.typeOfQuestion = typeOfQuestion;
        this.configurations = configurations;
        this.localizedHints = localizedHints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        localizedHints.forEach((key, value) -> value.setId(id));
    }

    public TypeOfHint getTypeOfHint() {
        return typeOfHint;
    }

    public void setTypeOfHint(TypeOfHint typeOfHint) {
        this.typeOfHint = typeOfHint;
        localizedHints.forEach((key, value) -> value.setTypeOfHint(typeOfHint));
    }

    public TypeOfQuestion getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(TypeOfQuestion typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
        localizedHints.forEach((key, value) -> value.setTypeOfQuestion(typeOfQuestion));
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
        localizedHints.forEach((key, value) -> value.setConfigurations(configurations));
    }

    public Map<Locale, LocalizedHint> getLocalizedHints() {
        return localizedHints;
    }

    public void setLocalizedHints(Map<Locale, LocalizedHint> localizedHints) {
        this.localizedHints = localizedHints;
    }

    /**
     * Compares two hints
     *
     * @param o Object to be compared with current hint
     * @return True if hints are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hint)) return false;
        Hint hint = (Hint) o;
        return Objects.equals(getId(), hint.getId()) &&
                getTypeOfHint() == hint.getTypeOfHint() &&
                getTypeOfQuestion() == hint.getTypeOfQuestion() &&
                Objects.equals(getLocalizedHints(), hint.getLocalizedHints());
    }

    /**
     * Calculates hash code of the hint
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTypeOfHint(), getTypeOfQuestion(), getLocalizedHints());
    }

    /**
     * Presents hint information in String type
     *
     * @return String representation of hint
     */
    @Override
    public String toString() {
        return "Hint{" +
                "id=" + id +
                ", typeOfHint=" + typeOfHint +
                ", typeOfQuestion=" + typeOfQuestion +
                ", configurations={" + configurations.stream().map(x -> String.valueOf(x.getId())).reduce("",(acc, x) -> acc + x +", ") + "}" +
                ", localizedHints={" + localizedHints.entrySet().stream().map(x -> x.getKey().getCountry() + " : " + x.getValue()).reduce("",(acc, x) -> acc + x +", ") + "}" +
                '}';
    }
}
