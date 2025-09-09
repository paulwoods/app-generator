package org.mrpaulwoods.backend.exceptions;

@SuppressWarnings("unused")
public class AppGeneratorException extends RuntimeException {

    public AppGeneratorException() {
        super();
    }

    public AppGeneratorException(String message) {
        super(message);
    }

    public AppGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppGeneratorException(Throwable cause) {
        super(cause);
    }

}
