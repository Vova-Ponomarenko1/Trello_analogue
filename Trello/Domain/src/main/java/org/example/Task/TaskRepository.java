package org.example.Task;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
    void createTask(Long columnId, String taskName, String description);
    void deleteTaskById(Long taskId);
    Task getTaskDetails(Long taskId);
    void updateTaskName(Long taskId, String newTaskName);
    void updateTaskDescription(Long taskId, String newTaskDescription);

    void synchronizeTaskPositions(List<TaskPositionDTO> taskPositions);
}
