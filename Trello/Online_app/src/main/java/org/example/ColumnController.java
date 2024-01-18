package org.example;

import org.example.Column.ColumnPositionDTO;
import org.example.Column.ColumnRepository;
import org.example.Column.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createColumn(@PathVariable Long boardId,@RequestBody ColumnRequest columnRequest) {
        try {
            //Todo дописати
            columnService.createColumn(boardId,columnRequest.getColumnName());
            return ResponseEntity.ok("Column created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create column");
        }
    }
    @PostMapping("/boards/synchronize-column-positions")
    public void synchronizeColumnPositions(@RequestBody List<ColumnPositionDTO> positions) {
        columnRepository.synchronizeColumnPositions(positions);
    }
    @PutMapping("/updateName/{columnId}")
    public ResponseEntity<String> updateColumnName(@PathVariable int columnId, @RequestBody Map<String, String> data) {
        columnService.updateColumnName(columnId, data.get("newName"));
        return ResponseEntity.ok("Column name updated successfully");
    }
    @DeleteMapping("/delete/{columnId}")
    public ResponseEntity<String> deleteColumn(@PathVariable Long columnId) {
        columnRepository.deleteColumnById(columnId);
        return ResponseEntity.ok("Column deleted successfully");
    }
}
