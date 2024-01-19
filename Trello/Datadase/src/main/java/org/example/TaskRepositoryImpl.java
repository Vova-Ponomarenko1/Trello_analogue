package org.example;

import org.example.Task.Task;
import org.example.Task.TaskPositionDTO;
import org.example.Task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void synchronizeTaskPositions(List<TaskPositionDTO> taskPositions) {
        String sql = "UPDATE tasks SET position = ?, column_id = ? WHERE task_id = ?";

        for (TaskPositionDTO position : taskPositions) {
            jdbcTemplate.update(sql, position.getPosition(), position.getColumnId(), position.getTaskId());
        }
    }
    @Override
    public Task getTaskDetails(Long taskId) {
        String sql = "SELECT task_name, task_description, created_at FROM tasks WHERE task_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, (resultSet, rowNum) -> {
            Task task = new Task();
            task.setTaskId(Math.toIntExact(taskId));
            task.setTaskName(resultSet.getString("task_name"));
            task.setTaskDescription(resultSet.getString("task_description"));
            task.setCreatedAt(Timestamp.valueOf(resultSet.getTimestamp("created_at").toLocalDateTime()));
            return task;
        });
    }

    @Override
    public void updateTaskName(Long taskId, String newTaskName) {
        String sql = "UPDATE tasks SET task_name = ? WHERE task_id = ?";
        jdbcTemplate.update(sql, newTaskName, taskId);
    }

    @Override
    public void updateTaskDescription(Long taskId, String newTaskDescription) {
        String sql = "UPDATE tasks SET task_description = ? WHERE task_id = ?";
        jdbcTemplate.update(sql, newTaskDescription, taskId);
    }

    @Override
    public void createTask(Long columnId, String taskName, String description) {
        String getBoardIdSql = "SELECT board_id FROM columns WHERE column_id = ?";
        Long boardId = jdbcTemplate.queryForObject(getBoardIdSql, Long.class, columnId);

        String getMaxPositionSql = "SELECT COALESCE(MAX(position), 0) FROM tasks WHERE column_id = ?";
        int maxPosition = jdbcTemplate.queryForObject(getMaxPositionSql, Integer.class, columnId);

        int newPosition = maxPosition + 1;
        String createTaskSql = "INSERT INTO tasks (column_id, task_name, task_description, board_id, position) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(createTaskSql, columnId, taskName, description, boardId, newPosition);
    }


    @Override
    public void deleteTaskById(Long taskId) {
        String sql = "DELETE FROM tasks WHERE task_id = ?";
        jdbcTemplate.update(sql, taskId);
    }
}
