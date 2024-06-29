package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.FenImplementer;
import org.example.Engine.BoardRepresentation.Move.Move;

public class PerftTestTest extends TestCase {

    public void testPerft() {
        assertEquals(1L, PerftTest.perft(BoardConstants.STARTING_FEN, 0, false));
        assertEquals(20L, PerftTest.perft(BoardConstants.STARTING_FEN, 1, false));
        assertEquals(400L, PerftTest.perft(BoardConstants.STARTING_FEN, 2, false));

        assertEquals(8_902L, PerftTest.perft(BoardConstants.STARTING_FEN, 3, false));

        assertEquals(197_281L, PerftTest.perft(BoardConstants.STARTING_FEN, 4, true));
//        assertEquals(4_865_609L, PerftTest.perft(BoardConstants.STARTING_FEN, 5, false));
//        assertEquals(119_060_324L, PerftTest.perft(BoardConstants.STARTING_FEN, 6, false));
//        assertEquals(3_195_901_860L, PerftTest.perft(BoardConstants.STARTING_FEN, 7, false));


    }

    // position startpos moves b2b3 e7e6 c1a3
    public void testTemp() {
        PerftTest.perft(BoardConstants.STARTING_FEN, 4, false);
        PerftTest.perft(BoardConstants.STARTING_FEN, 5, false);
        PerftTest.perft(BoardConstants.STARTING_FEN, 6, false);
        PerftTest.perft(BoardConstants.STARTING_FEN, 7, false);
        PerftTest.perft(BoardConstants.STARTING_FEN, 8, false);
        PerftTest.perft(BoardConstants.STARTING_FEN, 9, false);
    }
}
