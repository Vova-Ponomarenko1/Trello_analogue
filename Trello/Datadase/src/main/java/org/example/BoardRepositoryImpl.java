package org.example;

import org.example.Board.Board;
import org.example.Board.BoardRepository;
import org.example.Column.Column;
import org.example.Exception.BoardNotFoundException;
import org.example.Task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BoardRepositoryImpl implements BoardRepository {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public BoardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void createBoard(String name) {
        String sql = "INSERT INTO boards (board_name) VALUES (?)";
        jdbcTemplate.update(sql, name);
    }
    @Override
    public List<Board> getAllBoardIds() {
        String sql = "SELECT board_id, board_name FROM boards";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Board(resultSet.getLong("board_id"), resultSet.getString("board_name"))
        );
    }

    @Override
    public void reNameBoard(Long boardId, String newName) {
        try {
            String updateQuery = "UPDATE boards SET board_name = ? WHERE board_id = ?";
            jdbcTemplate.update(updateQuery, newName, boardId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to rename board with id: " + boardId, e);
        }
    }

    @Override
    public void deleteBoard(Long boardId) {
        try {
            String deleteBoardQuery = "DELETE FROM boards WHERE board_id = ?";
            jdbcTemplate.update(deleteBoardQuery, boardId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete board", e);
        }
    }

    @Override
    public List<List<Column>> getTasksAndColumnsForBoard(int boardId) {
        String sql = "SELECT c.*, t.*, c.position AS column_position FROM columns c " +
                "LEFT JOIN tasks t ON c.column_id = t.column_id AND t.board_id = ? " +
                "WHERE c.board_id = ? " +
                "ORDER BY c.position, t.position";

        return jdbcTemplate.query(sql, new Object[]{boardId, boardId}, new TaskColumnBoardMapper());
    }

    @Override
    public Long getRandomBoardId() throws BoardNotFoundException {
        try {
            String sql = "SELECT board_id FROM boards ORDER BY RANDOM() LIMIT 1";
            return jdbcTemplate.queryForObject(sql, Long.class);
        } catch (DataAccessException e) {
            throw new BoardNotFoundException("");
        }
    }



    private static class TaskColumnBoardMapper implements RowMapper<List<Column>> {
        @Override
        public List<Column> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<Long, Column> columnsMap = new LinkedHashMap<>();

            do {
                Long columnId = rs.getLong("column_id");

                if (!columnsMap.containsKey(columnId)) {
                    Column column = new Column();
                    column.setId(columnId);
                    column.setName(rs.getString("column_name"));
                    column.setPosition(rs.getInt("column_position"));
                    columnsMap.put(columnId, column);
                }

                if (rs.getInt("task_id") > 0) {
                    Task task = new Task();
                    task.setTaskId(rs.getInt("task_id"));
                    task.setColumnId(Math.toIntExact(columnId));
                    task.setTaskName(rs.getString("task_name"));
                    task.setTaskDescription(rs.getString("task_description"));
                    task.setCreatedAt(rs.getTimestamp("created_at"));

                    columnsMap.get(columnId).getTasks().add(task);
                }
            } while (rs.next());

            return new ArrayList<>(columnsMap.values());
        }
    }
}
