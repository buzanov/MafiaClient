package ru.informatics.jojomafia.protocol;

public enum RequestCommands {
    SIGN_UP,
    SIGN_IN,
    CREATE_ROOM,
    JOIN_ROOM,
    LEAVE_ROOM,
    START_GAME,
    SEND_MESSAGE,
    GET_ROOMS,
    GET_LOBBY_INFO,
    INTERACT_WITH

}
/*
CL: ROOM_NAME

SUCCESS PLAYER_INFO
(ROLE NAME)

SUCCESS GAME_INFO
(GameDTO (List<String> playerNames)
(State, Activity)


 */