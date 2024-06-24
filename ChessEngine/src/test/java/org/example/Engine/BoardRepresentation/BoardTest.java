package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Move.Move;

public class BoardTest extends TestCase {

    public void testAdditionAndDeletion() {
        Board board = new Board();
        board.startFromDefaultPosition();

        System.out.println(board);

        Move move1 = new Move("e2e4");
        board.makeMove(move1);
        System.out.println(board);

        Move move2 = new Move("e7e5");
        board.makeMove(move2);
        System.out.println(board);

        board.unmakeMove();
        System.out.println(board);

        board.unmakeMove();
        System.out.println(board);
    }

}