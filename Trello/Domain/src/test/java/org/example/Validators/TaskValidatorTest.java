package org.example.Validators;

import org.example.Exception.TaskValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TaskValidatorTest {

    private TaskValidator taskValidator;

    @BeforeEach
    public void setUp() {
        taskValidator = new TaskValidator();
    }

    @Test
    void testValidName() {
        taskValidator.taskValidateName("ValidName");
    }

    @Test
    void testNullName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName(null));
    }

    @Test
    void testEmptyName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName(""));
    }

    @Test
    void testLongName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName("a".repeat(46)));
    }

    @Test
    void testValidDescription() {
        taskValidator.taskValidateDescription("ValidDescription");
    }

    @Test
    void testNullDescription() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateDescription(null));
    }

    @Test
    void testLongDescription() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateDescription("a".repeat(1001)));
    }
}