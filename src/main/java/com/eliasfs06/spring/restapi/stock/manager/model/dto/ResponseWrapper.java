package com.eliasfs06.spring.restapi.stock.manager.model.dto;

public class ResponseWrapper<T> {
    private String message;
    private String type;
    private T data;

    public ResponseWrapper(String message, String type, T data) {
        this.message = message;
        this.type = type;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

