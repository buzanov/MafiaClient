package ru.informatics.jojomafia.protocol;

import ru.informatics.jojomafia.form.Form;

public class RequestsHandler {
    public static Request createRequest(Form form, String header) {
        Request request = new Request();
        request.setHeader(header);
        request.setPayloadFromMap(form.getParams());
        return request;
    }
}
