package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class MiddleSearcher implements Search {

    Board board;

    public MiddleSearcher(Board board) {
        this.board = board;
    }

    @Override
    public Move search() {
        return null;
    }
}
