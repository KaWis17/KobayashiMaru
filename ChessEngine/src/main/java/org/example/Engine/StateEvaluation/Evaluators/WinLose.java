package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.StateEvaluation.Evaluation;

public class WinLose implements Evaluation, BoardHelper {

    public static final int WIN = Integer.MAX_VALUE;
    public static final int LOSE = Integer.MIN_VALUE;

    Board board;

    public WinLose(Board board){
        this.board = board;
    }

    @Override
    public int evaluate() {


        return 0;
    }

}
