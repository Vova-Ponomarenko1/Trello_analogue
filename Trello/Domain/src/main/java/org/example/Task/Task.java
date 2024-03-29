package org.example.Task;

import java.sql.Timestamp;

public class Task {
    private int taskId;
    private int columnId;
    private String taskName;
    private String taskDescription;
    private Timestamp createdAt;

    public Task(int taskId, int columnId, String taskName, String taskDescription, Timestamp createdAt) {
        this.taskId = taskId;
        this.columnId = columnId;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.createdAt = createdAt;
    }

    public Task() {
    }

    public Task(String taskName, String taskDescription, Timestamp valueOf) {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
