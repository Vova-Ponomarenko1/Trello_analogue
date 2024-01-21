package org.example;

import org.example.Board.Board;
import org.example.Board.BoardRepository;
import org.example.Board.BoardService;
import org.example.Column.Column;
import org.example.Exception.BoardInvalidNameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BoardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardController boardController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    public void testGetCreateBoardPage() throws Exception {
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateBoard"));
    }

    @Test
    public void testGetTasksAndColumnsForBoard() throws Exception {
        int boardId = 1;

        List<List<Column>> mockColumns = new ArrayList<>();

        List<Board> nonEmptyBoards = new ArrayList<>();
        nonEmptyBoards.add(new Board());
        when(boardRepository.getAllBoardIds()).thenReturn(nonEmptyBoards);

        when(boardRepository.getTasksAndColumnsForBoard(boardId)).thenReturn(mockColumns);

        mockMvc.perform(get("/boards/{boardId}", boardId))
                .andExpect(status().isOk())
                .andExpect(view().name("BoardMenu"))
                .andExpect(model().attribute("columns", mockColumns))
                .andExpect(model().attributeExists("boards"));
    }

    @Test
    public void testGetTasksAndColumnsForBoardRedirect() throws Exception {
        int boardId = 1;

        when(boardRepository.getAllBoardIds()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/boards/{boardId}", boardId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/boards"));
    }

    @Test
    public void testCreateBoard() throws Exception {
        when(boardRepository.getRandomBoardId()).thenReturn(123L);

        Map<String, String> requestData = new HashMap<>();
        requestData.put("boardName", "Test Board");

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"boardName\":\"Test Board\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("123"));

        verify(boardService, times(1)).createBoard("Test Board");
    }

    @Test
    public void testUpdateBoardName() throws Exception {
        doNothing().when(boardService).reNameBoard(1L, "New Name");

        mockMvc.perform(put("/updateNameBoard/{boardId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newName\":\"New Name\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Board name updated successfully"));

        verify(boardService, times(1)).reNameBoard(1L, "New Name");
    }

    @Test
    public void testCreateBoardFailure() throws Exception {
        doThrow(new RuntimeException("Board creation failed")).when(boardService).createBoard(anyString());

        Map<String, String> requestData = new HashMap<>();
        requestData.put("boardName", "Test Board");

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"boardName\":\"Test Board\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to create board"));
    }

    @Test
    public void testUpdateBoardNameFailure() throws Exception {
        doThrow(new BoardInvalidNameException("Invalid name")).when(boardService)
                .reNameBoard(1L, "New Name");

        mockMvc.perform(put("/updateNameBoard/{boardId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newName\":\"New Name\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to rename board"));
    }

    @Test
    public void testDeleteBoardSuccess() throws Exception {
        doNothing().when(boardRepository).deleteBoard(1L);

        mockMvc.perform(delete("/delete/{boardId}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/boards"));

        verify(boardRepository, times(1)).deleteBoard(1L);
    }
}