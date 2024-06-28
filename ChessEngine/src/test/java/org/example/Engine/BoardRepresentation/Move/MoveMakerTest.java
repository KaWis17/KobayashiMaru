package org.example.Engine.BoardRepresentation.Move;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.State;


public class MoveMakerTest extends TestCase {

    private MoveMaker moveMaker;
    private Board board;
    State stateBeforeMove;

    public void setUp() throws Exception {
        board = new Board();
        board.startFromDefaultPosition();
        stateBeforeMove = board.currentBoardState;
        moveMaker = new MoveMaker(board);
    }

    public void testColorChangeAfterMove() {
        Move moveToMake = new Move("d2d4", board);
        moveMaker.makeMove(moveToMake);
        assertEquals(stateBeforeMove.whiteToMove, !board.currentBoardState.whiteToMove);
    }
}