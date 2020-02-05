package ru.informatics.jojomafia.form;

import java.util.Map;

public class EmptyForm implements Form {
    @Override
    public Map<String, Object> getParams() {
        return null;
    }
}
