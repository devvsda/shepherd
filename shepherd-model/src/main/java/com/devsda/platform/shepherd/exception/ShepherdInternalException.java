package com.devsda.platform.shepherd.exception;

public class ShepherdInternalException extends ShepherdException {

    public ShepherdInternalException(String message) {
        super(message);
    }

    public ShepherdInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShepherdInternalException(Throwable cause) {
        super(cause);
    }
}
