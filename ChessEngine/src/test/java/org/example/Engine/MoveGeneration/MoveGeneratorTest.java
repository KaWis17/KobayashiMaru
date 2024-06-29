package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class MoveGeneratorTest extends TestCase {

    public void testPerft() {
        Board board = new Board();
        board.startFromDefaultPosition();
        MoveGenerator moveGenerator = new MoveGenerator(board);

        board.makeMove(new Move("b1c3", board));
        board.makeMove(new Move("b8c6", board));
        board.unmakeMove();
        System.out.println(board.getEnPassantTarget());
        System.out.println(board);

        int sum = 0;
        for(Move move : moveGenerator.generateMoves()) {
            board.makeMove(move);
            sum += moveGenerator.generateMoves().size();
            board.unmakeMove();
        }

        System.out.println(sum);
    }
}