package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class EndSearcher implements Search {

    Board board;

    public EndSearcher(Board board) {
        this.board = board;
    }

    @Override
    public Move search() {
        return null;
    }

}
