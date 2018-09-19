package com.devsda.platform.shepherd.exception;

public class GraphLoaderException extends ShepherdInternalException {

    public GraphLoaderException(String message) {
        super(message);
    }

    public GraphLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphLoaderException(Throwable cause) {
        super(cause);
    }
}
