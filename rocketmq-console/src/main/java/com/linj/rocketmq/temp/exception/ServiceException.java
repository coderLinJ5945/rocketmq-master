package com.linj.rocketmq.temp.exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 9213584003139969215L;
    private int code;

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
