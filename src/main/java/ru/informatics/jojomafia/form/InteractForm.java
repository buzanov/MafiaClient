package ru.informatics.jojomafia.form;

import java.util.HashMap;
import java.util.Map;

public class InteractForm implements Form {
    private String roomName;
    private String playerName;

    public InteractForm(String roomName, String playerName) {
        this.roomName = roomName;
        this.playerName = playerName;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("roomName", roomName);
        result.put("playerName", playerName);
        return result;
    }
}
