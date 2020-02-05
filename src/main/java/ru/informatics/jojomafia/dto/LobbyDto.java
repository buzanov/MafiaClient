package ru.informatics.jojomafia.dto;

import ru.informatics.jojomafia.utils.ModelBuilder;

import java.util.List;

public class LobbyDto implements Dto{

    private List<RoomDto> rooms;
    private UserDto user;

    public LobbyDto(List<RoomDto> rooms, UserDto user) {
        this.rooms = rooms;
        this.user = user;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public static class Builder implements ModelBuilder<LobbyDto> {

        private List<RoomDto> rooms;
        private UserDto user;

        public Builder setRooms(List<RoomDto> rooms) {
            this.rooms = rooms;
            return this;
        }

        public Builder setUser(UserDto user) {
            this.user = user;
            return this;
        }

        @Override
        public LobbyDto build() {
            return new LobbyDto(this.rooms, this.user);
        }
    }
}
