package org.example.Column;

import org.example.Task.Task;

import java.util.ArrayList;
import java.util.List;


public class Column {

    private Long id;

    private String name;


    private List<Task> tasks;

    private int position;

    public Column() {
        this.tasks = new ArrayList<>();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
