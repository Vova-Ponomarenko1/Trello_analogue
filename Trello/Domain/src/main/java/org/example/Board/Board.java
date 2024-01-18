package org.example.Board;

public class Board {
    private long id;

    private String boardName;

    public Board(long id, String boardName) {
        this.id = id;
        this.boardName = boardName;
    }

    public Board() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
