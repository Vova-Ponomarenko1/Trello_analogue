package org.example;

import org.example.Column.ColumnRepository;
import org.example.Column.ColumnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ColumnControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private ColumnService columnService;

    @InjectMocks
    private ColumnController columnController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(columnController).build();
    }

    @Test
    void testCreateColumnSuccess() {
        Long boardId = 1L;
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setColumnName("New valid name");

        doNothing().when(columnService).createColumn(eq(boardId), anyString());
        ResponseEntity<Map<String, String>> response = columnController.createColumn(boardId, columnRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Column created successfully", response.getBody().get("message"));
    }

    @Test
    void testCreateColumnFailure() throws Exception {
        Long boardId = 1L;
        ColumnRequest columnRequest = new ColumnRequest();
        columnRequest.setColumnName("ad");
        doThrow(new RuntimeException("exception")).when(columnService).createColumn(eq(boardId), anyString());

        ResponseEntity<Map<String, String>> response = columnController.createColumn(boardId, columnRequest);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Choose the board in which you wish to create a column. " +
                "Additionally, ensure that the column name is a minimum of " +
                "3 characters and does not exceed 50 characters", response.getBody().get("message"));
    }

    @Test
    void testDeleteColumn() throws Exception {
        mockMvc.perform(delete("/api/column/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Column deleted successfully"));

        verify(columnRepository).deleteColumnById(1L);
    }
}