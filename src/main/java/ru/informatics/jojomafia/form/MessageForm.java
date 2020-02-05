package ru.informatics.jojomafia.form;

import java.util.HashMap;
import java.util.Map;

public class MessageForm implements Form {
    private String message;

    public MessageForm(String message) {
        this.message = message;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", message);
        return result;
    }
}
