package ru.informatics.jojomafia.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


import ru.informatics.jojomafia.utils.ModelBuilder;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserDto implements Dto{

    private Long id;
    private String login;
    private UserStats userStats;
    private String token;

    public UserDto(Long id, String login, UserStats userStats, String token) {
        this.id = id;
        this.login = login;
        this.userStats = userStats;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserStats getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStats userStats) {
        this.userStats = userStats;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
