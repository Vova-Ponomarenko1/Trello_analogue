package org.example;

import org.example.Exception.BoardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;


import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BoardRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BoardRepositoryImpl boardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createBoard_SuccessfullyCreatesBoard() {
        String boardName = "TestBoard";

        boardRepository.createBoard(boardName);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(boardName));
    }

    @Test
    void createBoard_FailsToCreateBoard() {
        String boardName = "TestBoard";

        when(jdbcTemplate.update(anyString(), eq(boardName))).thenReturn(0);

        boardRepository.createBoard(boardName);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(boardName));
    }

    @Test
    void deleteBoard_SuccessfullyDeletesBoard() {
        Long boardId = 1L;

        when(jdbcTemplate.update(anyString(), eq(boardId))).thenReturn(1);

        boardRepository.deleteBoard(boardId);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(boardId));
    }

    @Test
    void deleteBoard_FailsToDeleteBoard() {
        Long boardId = 1L;

        when(jdbcTemplate.update(anyString(), eq(boardId))).thenReturn(0);

        boardRepository.deleteBoard(boardId);

        verify(jdbcTemplate, times(1)).update(anyString(), eq(boardId));
    }

    @Test
    void getRandomBoardId_ReturnsValidBoardId() {
        Long expectedBoardId = 42L;

        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class)))
                .thenReturn(expectedBoardId);

        try {
            Long actualBoardId = boardRepository.getRandomBoardId();

            assertEquals(expectedBoardId, actualBoardId);

            verify(jdbcTemplate).queryForObject(anyString(), eq(Long.class));
        } catch (BoardNotFoundException e) {
            fail("didn't expect BoardNotFoundException to be thrown");
        }
    }

    @Test
    void getRandomBoardId_ThrowsBoardNotFoundException() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Long.class)))
                .thenThrow(new DataAccessException("simulated exception") {});

        assertThrows(BoardNotFoundException.class, () -> {
            boardRepository.getRandomBoardId();
        });

        verify(jdbcTemplate).queryForObject(anyString(), eq(Long.class));
    }
}
