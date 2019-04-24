package com.devsda.platform.shepherd.exception;

public class ClientNodeFailureException extends ShepherdExternalException {

    public ClientNodeFailureException(String message) {
        super(message);
    }

    public ClientNodeFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNodeFailureException(Throwable cause) {
        super(cause);
    }
}
