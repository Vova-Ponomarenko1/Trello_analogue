package org.example;

import org.example.Board.Board;
import org.example.Board.BoardRepository;
import org.example.Board.BoardService;
import org.example.Column.Column;
import org.example.Exception.BoardInvalidNameException;
import org.example.Exception.BoardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
        return new ModelAndView("CreateBoard");
    }
    @GetMapping("/boards/{boardId}")
    public ModelAndView getTasksAndColumnsForBoard(@PathVariable int boardId) {
        List<Board> boards = boardRepository.getAllBoardIds();
        if (CollectionUtils.isEmpty(boards)) {
            return new ModelAndView("redirect:/boards");
        }
        List<List<Column>> columns = boardRepository.getTasksAndColumnsForBoard(boardId);
        ModelAndView modelAndView = new ModelAndView("BoardMenu");
        modelAndView.addObject("columns", columns);
        modelAndView.addObject("boards", boards);
        return modelAndView;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@RequestBody Map<String, String> data) {
        try {
            String boardName = data.get("boardName");
            boardService.createBoard(boardName);
            return ResponseEntity.ok(boardRepository.getRandomBoardId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create board");
        }
    }
    @PutMapping("/updateNameBoard/{boardId}")
    public ResponseEntity<String> updateBoardName(@PathVariable Long boardId,
                                                  @RequestBody Map<String, String> data) {
        try {
            boardService.reNameBoard(boardId,  data.get("newName"));
            return ResponseEntity.ok("Board name updated successfully");
        } catch (BoardInvalidNameException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to rename board");
        }

    }
    @DeleteMapping("/delete/{boardId}")
    public ModelAndView deleteBoard(@PathVariable Long boardId) {
        boardRepository.deleteBoard(boardId);
        return new ModelAndView("redirect:/boards");
    }
}
