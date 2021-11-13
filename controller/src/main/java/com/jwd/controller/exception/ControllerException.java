package com.jwd.controller.exception;

public class ControllerException extends Exception {
    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Exception e) {
        super(e);
    }
}
