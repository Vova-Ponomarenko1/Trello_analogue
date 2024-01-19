package org.example.Validators;

import org.example.Exception.TaskValidateException;
import org.springframework.stereotype.Component;

@Component
public class TaskValidator {

    public void taskValidateName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() > 45) {
            throw new TaskValidateException("Task name is invalid. It should not be null, empty, or exceed 45 characters.");
        }
    }
    public void taskValidateDescription(String description) {
        if (description == null || description.length() > 1000) {
            throw new TaskValidateException("Task description is invalid. It should not be null and should " +
                    "not exceed 1000 characters.");
        }
    }
}
