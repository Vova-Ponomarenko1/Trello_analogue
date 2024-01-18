package org.example.Task;

import org.example.Task.Task;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    void updateTaskName(Long taskId, String newTaskName);
    void updateTaskDescription(Long taskId, String newTaskDescription);

    void createTask(Long columnId, String taskName, String description);
}
