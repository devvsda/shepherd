package com.devsda.platform.shepherd.exception;

public class ClientInvalidRequestException extends ShepherdExternalException {

    public ClientInvalidRequestException(String message) {
        super(message);
    }

    public ClientInvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientInvalidRequestException(Throwable cause) {
        super(cause);
    }
}
