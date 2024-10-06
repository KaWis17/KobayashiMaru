package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluation;

public class CheckBonus implements Evaluation {

    public static final int CHECK = 500;

    Board board;

    public CheckBonus(Board board){
        this.board = board;
    }

    @Override
    public int evaluate() {

        if(board.isWhiteToPlay() && board.isBlackInCheck())
            return CHECK;

        if(!board.isWhiteToPlay() && board.isWhiteInCheck())
            return -CHECK;

        return 0;
    }

}
