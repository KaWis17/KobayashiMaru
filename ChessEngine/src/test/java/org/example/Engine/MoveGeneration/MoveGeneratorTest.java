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

        ArrayList<Move> moves = moveGenerator.generateMoves();
        System.out.println(moves);

    }

}