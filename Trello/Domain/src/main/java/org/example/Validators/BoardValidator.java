package org.example.Validators;

import org.example.Exception.BoardInvalidNameException;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class BoardValidator {

    public void BoardValidators(String name) {

    }

    private void BoardValidName(String name) {
        if (name != null && !name.trim().isEmpty() && name.length() > 3 ) {
            throw new BoardInvalidNameException("");
        }
    }
}
