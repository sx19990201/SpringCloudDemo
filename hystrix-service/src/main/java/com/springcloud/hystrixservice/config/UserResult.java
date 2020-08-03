package com.springcloud.hystrixservice.config;

public class UserResult<T> {

    private T data;
    private String message;
    private Integer code;

    public UserResult() {
    }

    public UserResult(T data, String message, Integer code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public UserResult(String message, Integer code) {
        this(null, message, code);
    }

    public UserResult(T data) {
        this(data, "操作成功", 200);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
