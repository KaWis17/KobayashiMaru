package org.example.Engine.StateEvaluation;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluators.Material;
import org.example.Engine.StateEvaluation.Evaluators.PieceSquareTable;
import org.example.Engine.StateEvaluation.Evaluators.WinLose;

import java.util.ArrayList;

public class Evaluator {

    static ArrayList<Evaluation> evaluators = new ArrayList<>();
    Board board;
    public int counter;

    public Evaluator(Board board) {
        this.board = board;
        evaluators.add(new WinLose(board));
        evaluators.add(new Material(board));
        evaluators.add(new PieceSquareTable(board));
        counter = 0;
    }

    public int evaluate() {
        counter++;
        int evalForWhite = 0;

        for(Evaluation evaluator: evaluators) {
            evalForWhite += evaluator.evaluate();
        }

        return (board.isWhiteToPlay()) ? evalForWhite : -evalForWhite;
    }

    public int getCount () {
        int toReturn = counter;
        this.counter = 0;
        return toReturn;
    }
}
