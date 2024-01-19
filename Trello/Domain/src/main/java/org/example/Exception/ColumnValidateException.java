package org.example.Exception;

public class ColumnValidateException extends RuntimeException {

    public ColumnValidateException(String message) {
        super(message);
    }

    public ColumnValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
