package org.example.Validators;

import static org.junit.jupiter.api.Assertions.*;

import org.example.Exception.BoardInvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardValidatorTest {

    private BoardValidator boardValidator;

    @BeforeEach
    public void setUp() {
        boardValidator = new BoardValidator();
    }

    @Test
    void testValidName() {
        boardValidator.boardValidators("ValidName");
    }

    @Test
    void testNullName() {
        assertThrows(BoardInvalidNameException.class,
                () -> boardValidator.boardValidators(null));
    }

    @Test
    void testEmptyName() {
        assertThrows(BoardInvalidNameException.class,
                () -> boardValidator.boardValidators(""));
    }

    @Test
    void testShortName() {
        assertThrows(BoardInvalidNameException.class,
                () -> boardValidator.boardValidators("ab"));
    }

    @Test
    void testLongName() {
        assertThrows(BoardInvalidNameException.class,
                () -> boardValidator.boardValidators("a".repeat(51)));
    }
}
