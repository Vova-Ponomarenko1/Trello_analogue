package org.example.Board;

import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    void createBoard(String name);
    void reNameBoard(Long boardId,String newName);
}
