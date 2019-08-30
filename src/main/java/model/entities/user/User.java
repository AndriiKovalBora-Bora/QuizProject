/*
 * Copyright (C) 2019 Quiz Project
 */

package model.entities.user;

import model.entities.statistics.Statistics;

import java.io.Serializable;
import java.util.*;

/**
 * Presents user
 */
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private Role role;
    private Status status;
    private List<Statistics> statistics = new ArrayList<>();
    private Map<Locale, LocalizedUser> localizedUsers = new HashMap<>();

    public User() {
    }

    public User(Integer id, String email, String password, Role role, Status status, List<Statistics> statistics, Map<Locale, LocalizedUser> localizedUsers) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.statistics = statistics;
        this.localizedUsers = localizedUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        localizedUsers.forEach((key, value) -> value.setId(id));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        localizedUsers.forEach((key, value) -> value.setEmail(email));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        localizedUsers.forEach((key, value) -> value.setPassword(password));
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        localizedUsers.forEach((key, value) -> value.setRole(role));
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        localizedUsers.forEach((key, value) -> value.setStatus(status));
    }

    public List<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistics> statistics) {
        this.statistics = statistics;
        localizedUsers.forEach((key, value) -> value.setStatistics(statistics));
    }

    public Map<Locale, LocalizedUser> getLocalizedUsers() {
        return localizedUsers;
    }

    public void setLocalizedUsers(Map<Locale, LocalizedUser> localizedUsers) {
        this.localizedUsers = localizedUsers;
    }

    /**
     * Compares two users
     *
     * @param o Object to be compared with current user
     * @return True if users are equal, False otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                getRole() == user.getRole() &&
                getStatus() == user.getStatus();
    }

    /**
     * Calculates hash code of the user
     *
     * @return int hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getRole(), getStatus());
    }

    /**
     * Presents user information in String type
     *
     * @return String representation of user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", statisticsIds={" + statistics.stream().map(x -> String.valueOf(x.getId())).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                ", localizedUsers={" + localizedUsers.entrySet().stream().map(x -> x.getKey().getCountry() + " : " + x.getValue()).reduce("", (acc, x) -> acc + x + ", ") + "}" +
                '}';
    }
}
