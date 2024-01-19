package org.example;

import org.example.Column.ColumnPositionDTO;
import org.example.Column.ColumnRepository;
import org.example.Column.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/column")
public class ColumnController {

    private ColumnRepository columnRepository;

    private ColumnService columnService;
    @Autowired
    public ColumnController(ColumnRepository columnRepository, ColumnService columnService) {
        this.columnRepository = columnRepository;
        this.columnService = columnService;
    }
    @PostMapping("/create/{boardId}")
    public ResponseEntity<Map<String, String>> createColumn(@PathVariable Long boardId,
                                                            @RequestBody ColumnRequest columnRequest) {
        try {
            columnService.createColumn(boardId,columnRequest.getColumnName());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Column created successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "Choose the board in which you wish to create a column. " +
                            "Additionally, ensure that the column name is a minimum of " +
                            "3 characters and does not exceed 50 characters"));
        }
    }
    @PostMapping("/boards/synchronize-column-positions")
    public void synchronizeColumnPositions(@RequestBody List<ColumnPositionDTO> positions) {
        columnRepository.synchronizeColumnPositions(positions);
    }
    @PutMapping("/updateName/{columnId}")
    public ResponseEntity<Map<String, String>> updateColumnName(@PathVariable int columnId,
                                                                @RequestBody Map<String, String> data) {
       try {
           columnService.updateColumnName(columnId, data.get("newName"));
           Map<String, String> response = new HashMap<>();
           response.put("message", "Column created successfully");
           return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("message", "Choose the board in which you wish to create a column. " +
                            "Additionally, ensure that the column name is a minimum of " +
                            "3 characters and does not exceed 50 characters"));
        }

    }
    @DeleteMapping("/delete/{columnId}")
    public ResponseEntity<String> deleteColumn(@PathVariable Long columnId) {
        columnRepository.deleteColumnById(columnId);
        return ResponseEntity.ok("Column deleted successfully");
    }
}
