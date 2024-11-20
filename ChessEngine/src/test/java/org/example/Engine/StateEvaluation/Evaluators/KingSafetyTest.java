package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;

public class KingSafetyTest extends TestCase {

    public void testEvaluate() {
        Board board = new Board();
        board.startFromDefaultPosition();
//        board.startFromCustomPosition("7K/8/8/8/8/8/8/8 w - - 0 1");
        KingSafety kingSafety = new KingSafety(board, 1);

        System.out.println(kingSafety.evaluate());
    }
}