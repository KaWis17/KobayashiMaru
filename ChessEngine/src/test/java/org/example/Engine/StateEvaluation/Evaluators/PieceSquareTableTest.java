package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.StateEvaluation.Evaluation;

public class PieceSquareTableTest extends TestCase {

    public void testEvaluate() {
        Board board = new Board();
        board.startFromDefaultPosition();

        PieceSquareTable pieceSquareTable = new PieceSquareTable(board);
        int result = pieceSquareTable.evaluate();
        System.out.println(result);
    }
}