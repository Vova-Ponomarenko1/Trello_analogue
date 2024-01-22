package org.example.Validators;

import org.example.Exception.ColumnValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColumnValidatorTest {

    private ColumnValidator columnValidator;

    @BeforeEach
    public void setUp() {
        columnValidator = new ColumnValidator();
    }

    @Test
    void testValidName() {
        columnValidator.columnValidateName("ValidName");
    }

    @Test
    void testNullName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName(null));
    }

    @Test
    void testEmptyName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName(""));
    }

    @Test
    void testShortName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName("ab"));
    }

    @Test
    void testLongName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName("a".repeat(46)));
    }
}