/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.user;

import model.entities.statistics.Statistics;

import java.io.Serializable;
import java.util.*;

/**
 * Presents user in certain language
 */
public class LocalizedUser implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private Role role;
    private Status status;
    private List<Statistics> statistics = new ArrayList<>();
    private String name;
    private String surname;

    public LocalizedUser() {
    }

    public LocalizedUser(Integer id, String email, String password, Role role, Status status, List<Statistics> statistics, String name, String surname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.statistics = statistics;
        this.name = name;
        this.surname = surname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Compares two localized users
     *
     * @param o Object to be compared with current user
     * @return True if users are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalizedUser)) return false;
        LocalizedUser that = (LocalizedUser) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                getRole() == that.getRole() &&
                getStatus() == that.getStatus() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSurname(), that.getSurname());
    }

    /**
     * Calculates hash code of the localized user
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getRole(), getStatus(), getName(), getSurname());
    }

    /**
     * Presents user information in String type
     *
     * @return String representation of localized user
     */
    @Override
    public String toString() {
        return "LocalizedUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
