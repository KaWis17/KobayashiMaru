package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.FenImplementer;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;


public class MoveGeneratorTest extends TestCase implements BoardConstants {

    public void testPerft() {
        Board board = new Board();
        board.startFromCustomPosition("Q1r1k2r/1ppp1ppp/1b3nbN/1P6/BBnPP3/q4N2/Pp4PP/R2Q1RK1 w k - 0 3");
        MoveGenerator moveGenerator = new MoveGenerator(board);

        //board.makeMove(new Move("e4d6", board));
        System.out.println(board);
        ArrayList<Move> moves = moveGenerator.generateMoves();
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

        ArrayList<Move> moves = new MoveGenerator(board).generateMoves();


        for (Move move : moves) {
            String startFen = FenImplementer.BoardToFEN(board);
            board.makeMove(move);


            for (Move move1 : new MoveGenerator(board).generateMoves()) {
                String startFen1 = FenImplementer.BoardToFEN(board);
                board.makeMove(move1);

                board.unmakeMove();
                if(!startFen1.equals(FenImplementer.BoardToFEN(board))) {
                    System.out.println("FENs don't match on: " + move);
                }
            }


            board.unmakeMove();
            if(!startFen.equals(FenImplementer.BoardToFEN(board))) {
                System.out.println("FENs don't match on: " + move);
            }
        }

        System.out.println("unmake e8g8");
        board.unmakeMove();
    }
}