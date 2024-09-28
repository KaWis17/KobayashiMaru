package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.BoardRepresentation.Move.Move;

public class Result {

    public int score;
    public Move move;

    public Result(int score, Move move) {
        this.score = score;
        this.move = move;
    }
}
