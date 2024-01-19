package org.example.Task;

import org.example.Validators.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    private TaskValidator taskValidator;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskValidator taskValidator) {
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
    }
    @Override
    public void updateTaskName(Long taskId, String newTaskName) {
        taskValidator.taskValidateName(newTaskName);
        taskRepository.updateTaskName(taskId,newTaskName);
    }

    @Override
    public void updateTaskDescription(Long taskId, String description) {
        taskValidator.taskValidateDescription(description);
        taskRepository.updateTaskDescription(taskId, description);
    }

    @Override
    public void createTask(Long columnId, String taskName, String description) {
        taskValidator.taskValidateName(taskName);
        taskValidator.taskValidateDescription(description);
        taskRepository.createTask(columnId, taskName, description);
    }
}
