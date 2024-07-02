package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class MaterialTest extends TestCase {

    public void testMaterial() {
        Board board = new Board();
        board.startFromDefaultPosition();
        board.makeMove(new Move("d2d4", board));
        board.makeMove(new Move("e7e5", board));
        board.makeMove(new Move("d4e5", board));
        Material material = new Material(board);
        System.out.println(material.evaluate());
    }
}