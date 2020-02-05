package ru.informatics.jojomafia.protocol;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private String header;
    private Payload payload;
    private String token;

    public String getHeader() {
        return header;
    }

    public Payload getPayload() {
        return payload;
    }

    public <T> T getParameter(String parameterName) {
        if(payload.getParameters().containsKey(parameterName)) {
            return (T)payload.getParameters().get(parameterName);
        } else throw new IllegalArgumentException("No such parameter in payload");
    }

    public void setPayloadFromMap(Map<String, Object> payloadParameters) {
        this.payload = new Payload(payloadParameters);
    }

    public void setPayloadParameter(String parameterName, String object) {
        if(payload == null) {
            this.payload = new Payload();
        }
        payload.setParameter(parameterName, object);
    }

    public String getToken() {
        return token;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder payloadParameters = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, Object> entry: payload.getParameters().entrySet()) {
            payloadParameters.append(entry.getKey()).append(":\"").append(entry.getValue()).append("\"");
            if (i != payload.getParameters().entrySet().size() - 1) {
                payloadParameters.append(",");
            }
            i++;
        }
        return "{" +
                    "header:\"" + header + "\","+
                    "payload: {" +
                        payloadParameters +
                    "}," +
                    "token: \"" + token + "\"" +
                "}";
    }

    private class Payload {

        private Map<String, Object> parameters;

        public Payload(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        public Payload() {
            this.parameters = new HashMap<>();
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, ? super Object> parameters) {
            this.parameters = parameters;
        }

        public void setParameter(String parameterName, String value) {
            parameters.put(parameterName, value);
        }
    }
}
