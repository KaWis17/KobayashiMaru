package org.example.Engine.BoardRepresentation.Move;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.State;
import org.example.Engine.Engine;


public class MoveMakerTest extends TestCase {

    private MoveMaker moveMaker;
    private Board board;
    State stateBeforeMove;

    public void setUp() {
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

    public void testFENandMove() {
//        Board board1 = new Board();
//        board1.startFromCustomPosition("r4rk1/p1pqpppp/2ppbn2/4P3/8/1PNP1N2/P1P2PPP/R1BQK2R b KQ - 0 8");

        Board board2 = new Board();
        board2.startFromDefaultPosition();
        System.out.println(board);

        MoveMaker maker = new MoveMaker(board2);

        makeMoveWithPrint("e2e4", maker, board2);
        makeMoveWithPrint("d7d6", maker, board2);
        makeMoveWithPrint("f1b5", maker, board2);
        makeMoveWithPrint("b8c6", maker, board2);
        makeMoveWithPrint("b5c6", maker, board2);
        makeMoveWithPrint("b7c6", maker, board2);
        makeMoveWithPrint("g1f3", maker, board2);
        makeMoveWithPrint("g8f6", maker, board2);
        makeMoveWithPrint("d2d3", maker, board2);
        makeMoveWithPrint("c8e6", maker, board2);
        makeMoveWithPrint("b2b3", maker, board2);
        makeMoveWithPrint("d8d7", maker, board2);
        makeMoveWithPrint("b1c3", maker, board2);
        makeMoveWithPrint("e8c8", maker, board2);
        makeMoveWithPrint("e4e5", maker, board2);

        System.out.println(BoardHelper.BoardToFEN(board2));
//        assertEquals(FenImplementer.BoardToFEN(board1), FenImplementer.BoardToFEN(board2));
    }

    private void makeMoveWithPrint(String moveString, MoveMaker maker, Board board2) {
        System.out.println(" ");
        Move move = new Move(moveString, board2);
        System.out.println(move + "   " + move.type + "   " + move.departure + "   " + move.destination);
        maker.makeMove(move);
        System.out.println(board2);
        System.out.println(" ");
    }

    public void test(){
        Engine engine = new Engine();
        engine.initiateDefaultPosition();

        System.out.println("INITIAL HASH: " + board.zobristHashing.getHash());
        board.makeMove("d2d4");
        board.makeMove("e7e6");
        board.unmakeMove();
        board.unmakeMove();
    }
}