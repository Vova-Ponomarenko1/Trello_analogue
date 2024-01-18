package org.example.Exception;

public class BoardInvalidNameException extends RuntimeException {
    public BoardInvalidNameException(String message) {
        super(message);
    }

    public BoardInvalidNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
