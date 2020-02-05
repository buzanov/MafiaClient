package ru.informatics.jojomafia.protocol;

public enum  ResponseCommands {;
    public enum Name {
        CONNECTED,
        SIGN_IN,
        SIGN_UP,
        CREATE_ROOM,
        JOIN_ROOM,
        LEAVE_ROOM,
        START_GAME,
        RECEIVE_MESSAGE,
        RECEIVE_ROOMS,
        RECEIVE_LOBBY_INFO,
        GAME_INFO,
        PLAYER_INFO,
        UPDATE_ROOM_INFO,
        GAME_STATUS
    }

    public enum Type {
        SUCCESS,
        ERROR
    }
}
