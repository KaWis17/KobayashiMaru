package org.example.Engine.BoardRepresentation.BoardFormats;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.CohesionCheck;

public class CohesionCheckTest extends TestCase implements BoardConstants {

    public void testIsCohesive() {
        Board board = new Board();
        board.startFromDefaultPosition();

        boolean isCohesive = CohesionCheck.isCohesive(board);
        assertTrue(isCohesive);
    }
}