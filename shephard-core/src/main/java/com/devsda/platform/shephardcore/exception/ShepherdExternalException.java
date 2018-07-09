package com.devsda.platform.shephardcore.exception;

public class ShepherdExternalException extends ShepherdException {

    public ShepherdExternalException(String message) {
        super(message);
    }

    public ShepherdExternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShepherdExternalException(Throwable cause) {
        super(cause);
    }
}
