package ru.informatics.jojomafia.form;

import java.util.HashMap;
import java.util.Map;

public class RoomForm implements Form {
    public RoomForm(String roomName) {
        this.roomName = roomName;
    }

    private String roomName;

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("roomName", roomName);
        return result;
    }

}
