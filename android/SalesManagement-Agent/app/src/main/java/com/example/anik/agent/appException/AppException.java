package com.example.anik.agent.appException;

/**
 * Created by Anik on 03-Aug-15, 003.
 */
public class AppException extends RuntimeException {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
