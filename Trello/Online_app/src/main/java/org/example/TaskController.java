package org.example;

import org.example.Task.Task;
import org.example.Task.TaskPositionDTO;
import org.example.Task.TaskRepository;
import org.example.Task.TaskUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private TaskRepository taskRepository;
    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskDetails(@PathVariable Long taskId) {
       // TODO Помінять репозиторій
        Task task = taskRepository.getTaskDetails(taskId);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{taskId}/name")
    public ResponseEntity<String> updateTaskName(@PathVariable Long taskId, @RequestBody TaskUpdateRequest request) {
        System.out.println(request.getTaskName());
        taskRepository.updateTaskName(taskId, request.getTaskName());

        return ResponseEntity.ok("Task name updated successfully");
    }
    @PutMapping("/{taskId}/description")
    public ResponseEntity<String> updateTaskDescription(@PathVariable Long taskId, @RequestBody TaskUpdateRequest request) {
        taskRepository.updateTaskDescription(taskId, request.getTaskDescription());
        return ResponseEntity.ok("Task description updated successfully");
    }
    @PostMapping("/create/{columnId}")
    public ResponseEntity<String> createTask(@PathVariable Long columnId, @RequestBody TaskUpdateRequest taskRequest) {
        taskRepository.createTask(columnId, taskRequest.getTaskName(), taskRequest.getTaskDescription());

        return ResponseEntity.ok("Task created successfully");
    }
    @PostMapping("/boards/synchronize-task-positions")
    public ResponseEntity<String> synchronizeTaskPositions(@RequestBody List<TaskPositionDTO> taskPositions) {
        try {
            taskRepository.synchronizeTaskPositions(taskPositions);
            return new ResponseEntity<>("Task positions synchronized successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error synchronizing task positions: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteTaskById(taskId);
        return ResponseEntity.ok("Task description updated successfully");
    }
}

