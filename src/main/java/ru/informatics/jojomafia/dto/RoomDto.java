package ru.informatics.jojomafia.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RoomDto implements Dto{

    private int maxPlayers;
    private List<UserDto> userList;
    private int currentPlayers;
    private String name;
    private GamePackDto chosenGamePack;
    private boolean hasGameStarted;

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GamePackDto getChosenGamePack() {
        return chosenGamePack;
    }

    public void setChosenGamePack(GamePackDto chosenGamePack) {
        this.chosenGamePack = chosenGamePack;
    }

    public boolean isHasGameStarted() {
        return hasGameStarted;
    }

    public void setHasGameStarted(boolean hasGameStarted) {
        this.hasGameStarted = hasGameStarted;
    }

    private RoomDto(int maxPlayers, List<UserDto> userList, String name, boolean hasGameStarted) {
        this.maxPlayers = maxPlayers;
        this.userList = userList;
        this.name = name;
        this.hasGameStarted = hasGameStarted;
    }

    public RoomDto() {
    }
}
