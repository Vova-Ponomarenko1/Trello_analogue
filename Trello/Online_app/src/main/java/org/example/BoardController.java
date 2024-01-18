package org.example;

import org.example.Board.Board;
import org.example.Board.BoardRepository;
import org.example.Board.BoardService;
import org.example.Column.Column;
import org.example.Exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    @Autowired
    public BoardController(BoardService boardService,
                           BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }
    @GetMapping("/boards")
    public ModelAndView getCreateBoardPage() {
        try {
            return new ModelAndView("redirect:/boards/" + boardRepository.getRandomBoardId());
        } catch (BoardNotFoundException e) {
            return new ModelAndView("CreateBoard");
        }
    }
    @GetMapping("/boards/{boardId}")
    public ModelAndView getTasksAndColumnsForBoard(@PathVariable int boardId) {
        ModelAndView modelAndView = new ModelAndView("BoardMenu");

        List<Board> boards = boardRepository.getAllBoardIds();
        List<List<Column>> columns = boardRepository.getTasksAndColumnsForBoard(boardId);

        modelAndView.addObject("columns", columns);
        modelAndView.addObject("boards", boards);
        return modelAndView;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody String boardName) {
        try {
            boardService.createBoard(boardName);
            return ResponseEntity.ok("Board created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create board");
        }
    }
    @PutMapping("/updateNameBoard/{boardId}")
    public ResponseEntity<String> updateBoardName(@PathVariable Long boardId, @RequestBody Map<String, String> data) {
        boardService.reNameBoard(boardId,  data.get("newName"));
        return ResponseEntity.ok("Board name updated successfully");
    }
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        boardRepository.deleteBoard(boardId);
        return ResponseEntity.ok("Column deleted successfully");
    }
}
