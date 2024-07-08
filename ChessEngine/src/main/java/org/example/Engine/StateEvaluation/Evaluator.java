package org.example.Engine.StateEvaluation;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluators.Material;
import org.example.Engine.StateEvaluation.Evaluators.PieceSquareTable;

import java.util.ArrayList;

public class Evaluator {

    static ArrayList<Evaluation> evaluators = new ArrayList<>();
    Board board;

    public Evaluator(Board board) {
        this.board = board;
        evaluators.add(new Material(board));
        evaluators.add(new PieceSquareTable(board));
    }

    public int evaluate() {
        int evalForWhite = 0;

        for(Evaluation evaluator: evaluators) {
            evalForWhite += evaluator.evaluate();
        }

        return (board.isWhiteToPlay()) ? evalForWhite : -evalForWhite;
    }
}
