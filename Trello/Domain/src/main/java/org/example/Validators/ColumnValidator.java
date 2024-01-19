package org.example.Validators;

import org.example.Exception.ColumnValidateException;
import org.example.Exception.TaskValidateException;
import org.springframework.stereotype.Component;

@Component
public class ColumnValidator {

    public void columnValidateName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 45 || name.length() < 3) {
            throw new ColumnValidateException("Column name is invalid. It should not be null, empty, or exceed 45 characters.");
        }
    }
}
