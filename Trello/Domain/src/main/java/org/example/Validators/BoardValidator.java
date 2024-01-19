package org.example.Validators;

import org.example.Exception.BoardInvalidNameException;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class BoardValidator {

    public void boardValidators(String name) {
        boardValidName(name);
    }

    private void boardValidName(String name) {
        if (name == null || name.trim().isEmpty() || name.length() < 3 || name.length() > 50) {
            throw new BoardInvalidNameException("Invalid board name");
        }
    }
}
