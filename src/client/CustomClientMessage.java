package client;

import java.io.Serializable;

public class CustomClientMessage implements Serializable {
    private Object payload;
    private String message;

    public CustomClientMessage(String message, Object payload) {
        this.payload = payload;
        this.message = message;
    }

    public CustomClientMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
