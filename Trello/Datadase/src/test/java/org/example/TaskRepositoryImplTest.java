package org.example;

import org.example.Task.Task;
import org.example.Task.TaskPositionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class TaskRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TaskRepositoryImpl taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void synchronizeTaskPositions() {
        List<TaskPositionDTO> taskPositions = Collections.singletonList(new TaskPositionDTO());
        taskPositions.get(0).setTaskId(1L);
        taskPositions.get(0).setColumnId(2L);
        taskPositions.get(0).setPosition(3);
        taskRepository.synchronizeTaskPositions(taskPositions);

        verify(jdbcTemplate).update(anyString(), eq(3), eq(2L), eq(1L));
    }
    @Test
    void updateTaskName() {
        Long taskId = 1L;
        String newTaskName = "NewTaskName";

        taskRepository.updateTaskName(taskId, newTaskName);

        verify(jdbcTemplate).update(anyString(), eq(newTaskName), eq(taskId));
    }

    @Test
    void updateTaskDescription() {
        Long taskId = 1L;
        String newTaskDescription = "NewTaskDescription";

        taskRepository.updateTaskDescription(taskId, newTaskDescription);

        verify(jdbcTemplate).update(anyString(), eq(newTaskDescription), eq(taskId));
    }

    @Test
    void createTask() {
        Long columnId = 1L;
        String taskName = "TaskName";
        String description = "TaskDescription";

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class), eq(columnId))).thenReturn(2L);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(columnId))).thenReturn(1);

        taskRepository.createTask(columnId, taskName, description);

        verify(jdbcTemplate).update(anyString(), eq(columnId),
                eq(taskName), eq(description), eq(2L), eq(2));
    }

    @Test
    void deleteTaskById() {
        Long taskId = 1L;

        taskRepository.deleteTaskById(taskId);

        verify(jdbcTemplate).update(anyString(), eq(taskId));
    }
}
