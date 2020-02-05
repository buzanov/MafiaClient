package ru.informatics.jojomafia.form;

import java.util.HashMap;
import java.util.Map;

public class CreateRoomForm implements Form {
    private int amountOfPlayers;
    private String gamePack;
    private String roomName;

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("maxPlayers", amountOfPlayers);
        result.put("chosenGamePack", gamePack);
        result.put("name", roomName);
        return result;
    }

    public CreateRoomForm(int amountOfPlayers, String gamePack, String roomName) {
        this.amountOfPlayers = amountOfPlayers;
        this.gamePack = gamePack;
        this.roomName = roomName;
    }
}
