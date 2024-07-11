package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;


public class MoveGeneratorTest extends TestCase implements BoardHelper {

    public void testPerft() {
        Board board = new Board();
        board.startFromCustomPosition("3BK3/1P6/3pk1Br/2Pp2p1/7B/6P1/2Q1Pr2/6R1 b - - 0 1");
        MoveGenerator moveGenerator = new MoveGenerator(board);

        //board.makeMove(new Move("e4d6", board));
        System.out.println(board);
        ArrayList<Move> moves = moveGenerator.generateAllLegalMoves();
        for(Move move: moves) {
            System.out.println(move);
        }
//        int c = 0;
//        for (Move move : moves) {
//            System.out.println(move);
//            board.makeMove(move);
//            ArrayList<Move> childMoves = moveGenerator.generateMoves();
//            c+=childMoves.size();
//            System.out.println("\t" + childMoves.size());
//            System.out.println("\t" + childMoves);
//
//            board.unmakeMove();
//        }



//        System.out.println("Total: " + c);


    }

    public void testPerft2() {
        Board board = new Board();
        board.startFromCustomPosition("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K1R1 b Qkq - 1 1");

        System.out.println("e8g8");
        board.makeMove(new Move("e8g8", board));

        ArrayList<Move> moves = new MoveGenerator(board).generateAllLegalMoves();


        for (Move move : moves) {
            String startFen = BoardHelper.BoardToFEN(board);
            board.makeMove(move);


            for (Move move1 : new MoveGenerator(board).generateAllLegalMoves()) {
                String startFen1 = BoardHelper.BoardToFEN(board);
                board.makeMove(move1);

                board.unmakeMove();
                if(!startFen1.equals(BoardHelper.BoardToFEN(board))) {
                    System.out.println("FENs don't match on: " + move);
                }
            }


            board.unmakeMove();
            if(!startFen.equals(BoardHelper.BoardToFEN(board))) {
                System.out.println("FENs don't match on: " + move);
            }
        }

        System.out.println("unmake e8g8");
        board.unmakeMove();
    }
}