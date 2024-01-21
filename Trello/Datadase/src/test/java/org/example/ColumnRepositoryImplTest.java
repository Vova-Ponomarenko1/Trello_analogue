package org.example;

import org.example.Column.ColumnPositionDTO;
import org.example.Exception.BoardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ColumnRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ColumnRepositoryImpl columnRepositoryImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createColumn_Successful() {
        Long boardId = 1L;
        String columnName = "TestColumn";

        columnRepositoryImpl.createColumn(boardId, columnName);

        verify(jdbcTemplate, times(1))
                .update(eq("INSERT INTO columns (column_name, position, board_id) VALUES (?, 0, ?)"),
                        eq(columnName), eq(boardId));
    }

    @Test
    void createColumn_BoardNotFoundException() {
        Long boardId = 1L;
        String columnName = "Test Column";

        doThrow(new DataIntegrityViolationException("")).when(jdbcTemplate)
                .update(any(String.class),
                any(String.class), any(Long.class));

        try {
            columnRepositoryImpl.createColumn(boardId, columnName);
        } catch (BoardNotFoundException e) {
            return;
        }

        fail("Expected BoardNotFoundException but it was not thrown");
    }

    @Test
    void deleteColumnById_Success() {
        long columnId = 1L;

        columnRepositoryImpl.deleteColumnById(columnId);

        verify(jdbcTemplate).update("DELETE FROM tasks WHERE column_id = ?", columnId);
        verify(jdbcTemplate).update("DELETE FROM columns WHERE column_id = ?", columnId);
    }

    @Test
    void deleteColumnById_Failure() {
        long columnId = 1L;
        doThrow(new DataAccessException("Simulated exception") {})
                .when(jdbcTemplate).update(any(), (Object) any());

        assertThrows(RuntimeException.class,
                () -> columnRepositoryImpl.deleteColumnById(columnId));
    }
}