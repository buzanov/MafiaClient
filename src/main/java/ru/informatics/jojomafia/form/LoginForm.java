package ru.informatics.jojomafia.form;

import java.util.HashMap;
import java.util.Map;

public class LoginForm implements Form {
    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        result.put("login", login);
        result.put("password", password);
        return result;
    }

    private String login;
    private String password;
}
