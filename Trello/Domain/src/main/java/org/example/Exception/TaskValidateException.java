package org.example.Exception;

public class TaskValidateException extends RuntimeException {
    public TaskValidateException(String message) {
        super(message);
    }

    public TaskValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
