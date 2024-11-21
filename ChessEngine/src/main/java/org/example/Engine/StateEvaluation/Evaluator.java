package org.example.Engine.StateEvaluation;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluators.*;

import java.util.ArrayList;

public class Evaluator {

    static ArrayList<Evaluation> evaluators = new ArrayList<>();
    Board board;
    public int counter;

    public Evaluator(Board board) {
        this.board = board;
        evaluators.add(new CheckBonus(board, 0));
        evaluators.add(new Material(board, 5));
        evaluators.add(new PieceSquareTable(board, 3));
        evaluators.add(new KingSafety(board, 5));
        evaluators.add(new PawnsStructure(board, 5));
        counter = 0;
    }

    public int evaluate(int depthPassed) {
        counter++;
        int evalForWhite = 0;

        if(board.isDraw()) {
            if(depthPassed % 2 == 1)
                return 10_000;
            else
                return -10_000;
        }

        if(board.isBlackInCheckMate() || board.isWhiteInCheckMate()) {
            return -100_000_000;
        }

        for(Evaluation evaluator: evaluators) {
            evalForWhite += evaluator.evaluate();
        }

        return (board.isWhiteToPlay()) ? (evalForWhite) : (-evalForWhite);
    }

    public int getCount () {
        int toReturn = counter;
        this.counter = 0;
        return toReturn;
    }
}
