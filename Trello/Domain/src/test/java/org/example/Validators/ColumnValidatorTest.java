package org.example.Validators;

import org.example.Exception.ColumnValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnValidatorTest {

    private ColumnValidator columnValidator;

    @BeforeEach
    public void setUp() {
        columnValidator = new ColumnValidator();
    }

    @Test
    public void testValidName() {

        columnValidator.columnValidateName("ValidName");
    }

    @Test
    public void testNullName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName(null));
    }

    @Test
    public void testEmptyName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName(""));
    }

    @Test
    public void testShortName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName("ab"));
    }

    @Test
    public void testLongName() {
        assertThrows(ColumnValidateException.class,
                () -> columnValidator.columnValidateName("a".repeat(46)));
    }
}