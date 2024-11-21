package org.example.Engine.StateEvaluation;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class EvaluatorTest extends TestCase {

    public void testEvaluate() {
        Board board = new Board();
        board.startFromDefaultPosition();

        Evaluator evaluator = new Evaluator(board);

        board.makeMove(new Move("d2d4", board));
        board.makeMove(new Move("d7d5", board));
        board.makeMove(new Move("e2e4", board));
        board.makeMove(new Move("d5e4", board));


//        int result = evaluator.evaluate();
//        System.out.println(result);
    }
}