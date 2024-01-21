package org.example.Validators;

import org.example.Exception.TaskValidateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskValidatorTest {

    private TaskValidator taskValidator;

    @BeforeEach
    public void setUp() {
        taskValidator = new TaskValidator();
    }

    @Test
    public void testValidName() {
        taskValidator.taskValidateName("ValidName");
    }

    @Test
    public void testNullName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName(null));
    }

    @Test
    public void testEmptyName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName(""));
    }

    @Test
    public void testLongName() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateName("a".repeat(46)));
    }

    @Test
    public void testValidDescription() {
        taskValidator.taskValidateDescription("ValidDescription");
    }

    @Test
    public void testNullDescription() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateDescription(null));
    }

    @Test
    public void testLongDescription() {
        assertThrows(TaskValidateException.class,
                () -> taskValidator.taskValidateDescription("a".repeat(1001)));
    }
}