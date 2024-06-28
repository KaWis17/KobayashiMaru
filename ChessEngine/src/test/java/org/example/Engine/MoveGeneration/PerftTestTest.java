package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.BoardConstants;

public class PerftTestTest extends TestCase {

    public void testPerft() {
        PerftTest.perft(BoardConstants.STARTING_FEN, 2);
    }
}