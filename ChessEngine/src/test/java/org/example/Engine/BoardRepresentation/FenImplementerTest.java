package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Move.Move;

public class FenImplementerTest extends TestCase {

    public void testFenToBoard() {
        Board board = new Board();
        FenImplementer.implement(board, "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        System.out.println(board);
    }

    public void testBoardToFen() {
        Board board = new Board();
        board.startFromDefaultPosition();

        System.out.println(FenImplementer.generateFEN(board));

        board.makeMove(new Move("e2e4"));
        System.out.println(FenImplementer.generateFEN(board));

        board.makeMove(new Move("d7d6"));
        System.out.println(FenImplementer.generateFEN(board));
    }
}