package com.devsda.platform.shepherd.exception;

public class NodeFailureException extends ShepherdInternalException {

    public NodeFailureException(String message) {
        super(message);
    }

    public NodeFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public NodeFailureException(Throwable cause) {
        super(cause);
    }
}
