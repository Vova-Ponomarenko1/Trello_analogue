package org.example.Board;

import org.example.Validators.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardValidator boardValidator;
    private final BoardRepository boardRepository;
    @Autowired
    public BoardServiceImpl(BoardValidator boardValidator,
                            BoardRepository boardRepository) {
        this.boardValidator = boardValidator;
        this.boardRepository = boardRepository;
    }
    @Override
    public void createBoard(String name) {
        boardValidator.boardValidators(name);
        boardRepository.createBoard(name);
    }

    @Override
    public void reNameBoard(Long boardId, String newName) {
        boardValidator.boardValidators(newName);
        boardRepository.reNameBoard(boardId, newName);
    }


}
