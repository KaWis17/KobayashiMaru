package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;

public class PawnsStructureTest extends TestCase {

    public void testEvaluate() {
        Board board = new Board();
//        board.startFromCustomPosition("8/P6P/8/8/2P6/1P1P3P/P1P3P/8 w - - 0 1");
//        board.startFromCustomPosition("8/p1p5/1p1p3p/8/8/8/8/8 w - - 0 1");
//        board.startFromCustomPosition("8/8/8/8/3P4/8/8/8 w - - 0 1");

//        board.startFromCustomPosition("8/8/8/8/8/8/3P4/8 w - - 0 1");
        System.out.println(board);
        PawnsStructure pawnsStructure = new PawnsStructure(board, 1);
        System.out.println(pawnsStructure.evaluate());
    }

    public void testGenerateIsolatedMask() {
        Board board = new Board();
        PawnsStructure pawnsStructure = new PawnsStructure(board, 1);
    }
}