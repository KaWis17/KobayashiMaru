package org.example.Engine.StateEvaluation.Evaluators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class MaterialTest extends TestCase {

    public void testMaterial() {
        Board board = new Board();
        board.startFromCustomPosition("8/8/8/8/8/8/8/5K2 w - - 0 1");
        System.out.println(board);

        Material material = new Material(board, 1);
        System.out.println(material.evaluate());
    }
}