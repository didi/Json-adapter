package com.didi.middleware.json.adapter;

@SuppressWarnings("serial")
public class JSONException extends RuntimeException {

    public JSONException() {
        super();
    }

    public JSONException(String message) {
        super(message);
    }

    public JSONException(String message, Throwable cause) {
        super(message, cause);
    }
}
