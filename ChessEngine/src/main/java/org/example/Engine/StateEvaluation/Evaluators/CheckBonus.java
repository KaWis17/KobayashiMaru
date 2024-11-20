package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluation;

public class CheckBonus implements Evaluation {

    public static final int CHECK = 500;


    Board board;
    int weight;

    public CheckBonus(Board board, int weight){
        this.board = board;
        this.weight = weight;
    }

    @Override
    public int evaluate() {

        if(board.isWhiteToPlay() && board.isBlackInCheck())
            return weight * CHECK;

        if(!board.isWhiteToPlay() && board.isWhiteInCheck())
            return weight * -CHECK;

        return 0;
    }

}
