package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Task.Task;
import org.example.Task.TaskRepository;
import org.example.Task.TaskService;
import org.example.Task.TaskUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class TaskControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testGetTaskDetails() throws Exception {
        Task task = new Task();
        task.setTaskId(1);
        when(taskRepository.getTaskDetails(anyLong())).thenReturn(task);

        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        String response = mockMvc.perform(get("/api/task/{taskId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Task actualTask = objectMapper.readValue(response, Task.class);


        Task expectedTask = new Task();
        expectedTask.setTaskId(1);
        assertEquals(expectedTask.getTaskId(), actualTask.getTaskId());
    }

    @Test
    void testGetNonExistingTask() throws Exception {
        when(taskRepository.getTaskDetails(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/task/{taskId}", 1))
                .andExpect(status().isNotFound());
    }
    @Test
    void testUpdateTaskName() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTaskName("New Task Name");

        mockMvc.perform(put("/api/task/{taskId}/name", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task name updated successfully"));
    }

    @Test
    void testUpdateTaskDescription() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTaskDescription("New Task Description");

        mockMvc.perform(put("/api/task/{taskId}/description", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task description updated successfully"));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/task/delete/{taskId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Task description updated successfully"));
    }

    @Test
    void testCreateTask() throws Exception {
        TaskUpdateRequest request = new TaskUpdateRequest();
        request.setTaskName("New Task");

        mockMvc.perform(post("/api/task/create/{columnId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task created successfully"));
    }
}