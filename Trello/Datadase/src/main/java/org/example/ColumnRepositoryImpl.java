package org.example;

import org.example.Column.ColumnPositionDTO;
import org.example.Column.ColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ColumnRepositoryImpl implements ColumnRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ColumnRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createColumn(Long boardId, String columnName) {
        try {
            String createColumnQuery = "INSERT INTO columns (column_name, position, board_id) VALUES (?, 0, ?)";
            jdbcTemplate.update(createColumnQuery, columnName, boardId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create column", e);
        }
    }


    @Override
    public void updateColumnName(int columnId, String updateName) {
        try {
            String updateColumnNameQuery = "UPDATE columns SET column_name = ? WHERE column_id = ?";
            jdbcTemplate.update(updateColumnNameQuery, updateName, columnId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update column name", e);
        }
    }


    @Override
    public void deleteColumnById(Long columnId) {
        try {
            String deleteTasksQuery = "DELETE FROM tasks WHERE column_id = ?";
            jdbcTemplate.update(deleteTasksQuery, columnId);

            String deleteColumnQuery = "DELETE FROM columns WHERE column_id = ?";
            jdbcTemplate.update(deleteColumnQuery, columnId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete column with id: " + columnId, e);
        }
    }

    @Override
    public void synchronizeColumnPositions(List<ColumnPositionDTO> positions) {
        String sql = "UPDATE columns SET position = ? WHERE column_id = ?";

        for (ColumnPositionDTO position : positions) {
            jdbcTemplate.update(sql, position.getPosition(), position.getColumnId());
        }
    }
}
