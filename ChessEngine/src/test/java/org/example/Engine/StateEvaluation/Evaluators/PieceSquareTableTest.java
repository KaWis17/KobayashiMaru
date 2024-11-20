package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;

public class PieceSquareTableTest extends TestCase {

    public void testEvaluate() {
        Board board = new Board();
        board.startFromCustomPosition("8/8/8/8/8/8/4P3/8 w - - 0 1");

        PieceSquareTable pieceSquareTable = new PieceSquareTable(board, 1);
        int result = pieceSquareTable.evaluate();
        System.out.println(result);
        System.out.println(board);
    }
}