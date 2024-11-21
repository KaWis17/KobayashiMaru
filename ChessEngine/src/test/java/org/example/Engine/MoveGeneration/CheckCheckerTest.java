package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;

public class CheckCheckerTest extends TestCase {

    public void testIsWhiteInCheck() {
    }

    public void testIsWhiteInCheckMate() {
    }

    public void testIsBlackInCheck() {
    }

    public void testIsBlackInCheckMate() {
        Board board = new Board();
        board.startFromCustomPosition("2kr4/2R5/8/4B3/8/3N1P2/K4P2/8 b - - 0 68");
        System.out.println(board);
        CheckChecker checkChecker = new CheckChecker(board);
        System.out.println(checkChecker.isBlackInCheckMate());
    }

    public void testIsColorInCheck() {
    }

    public void testIsCheckMate() {
    }
}