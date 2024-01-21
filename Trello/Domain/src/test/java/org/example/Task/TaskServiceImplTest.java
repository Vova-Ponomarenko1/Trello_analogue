package org.example.Task;


import org.example.Validators.TaskValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskValidator taskValidator;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateTaskName_ValidName() {
        Long taskId = 1L;
        String newTaskName = "NewTaskName";

        taskService.updateTaskName(taskId, newTaskName);

        verify(taskValidator).taskValidateName(newTaskName);
        verify(taskRepository).updateTaskName(taskId, newTaskName);
    }

    @Test
    public void testUpdateTaskNameWithNullName() {
        Long taskId = 1L;
        String newTaskName = null;

        taskService.updateTaskName(taskId, newTaskName);

        verify(taskValidator).taskValidateName(newTaskName);

        verify(taskRepository, times(0)).updateTaskName(anyLong(), anyString());
    }

    @Test
    public void testUpdateTaskDescription() {
        Long taskId = 1L;
        String description = "NewDescription";

        taskService.updateTaskDescription(taskId, description);

        verify(taskValidator).taskValidateDescription(description);
        verify(taskRepository).updateTaskDescription(taskId, description);
    }

    @Test
    public void testCreateTask() {
        Long columnId = 1L;
        String taskName = "TaskName";
        String description = "TaskDescription";

        taskService.createTask(columnId, taskName, description);

        verify(taskValidator).taskValidateName(taskName);
        verify(taskValidator).taskValidateDescription(description);
        verify(taskRepository).createTask(columnId, taskName, description);
    }
}