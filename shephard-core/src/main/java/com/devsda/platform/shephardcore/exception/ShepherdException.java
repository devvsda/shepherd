package com.devsda.platform.shephardcore.exception;

public class ShepherdException extends RuntimeException {

    public ShepherdException(String message) {
        super(message);
    }

    public ShepherdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShepherdException(Throwable cause) {
        super(cause);
    }
}
