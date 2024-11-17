package com.tracabilite.tracabilite.web.common.utils;

public class HttpMessageResponse {
    private String message;

    public HttpMessageResponse() {
    }

    public HttpMessageResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
