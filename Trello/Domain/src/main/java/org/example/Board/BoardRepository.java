package org.example.Board;

import org.example.Column.Column;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {

    void createBoard(String name);

    List<Board> getAllBoardIds();

    void reNameBoard(Long boardId,String newName);

    void deleteBoard(Long boardId);

    List<List<Column>> getTasksAndColumnsForBoard(int boardId);
    Long getRandomBoardId();
}
