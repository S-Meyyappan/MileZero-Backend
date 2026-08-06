package com.springboot.milezero.exception;

public class InvalidReqException extends RuntimeException {
    public InvalidReqException(String message) {
        super(message);
    }
}
