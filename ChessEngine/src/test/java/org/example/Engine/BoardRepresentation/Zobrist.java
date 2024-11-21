package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;

import java.util.ArrayList;
import java.util.HashMap;

public class Zobrist extends TestCase {
    HashMap<Long, String> testForCollision;
    Board board;
    MoveGenerator generator;

    public void testCase() {
        board = new Board();
        board.startFromCustomPosition("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        generator = new MoveGenerator(board);
        testForCollision = new HashMap<>();
        perft(4);
        System.out.println("SIZE: " + testForCollision.size());
    }

    public void testCompareHashing() {
        board = new Board();
        board.startFromDefaultPosition();
        System.out.println(board.zobristHashing.getHash());
        System.out.println(board.zobristHashing.getTranspositionHash());
        String[] moves = "d2d4 g8f6 c2c4 e7e6 b1c3 f8b4 d1c2 e8g8 g1f3 b8c6 e2e3 b4c3 b2c3 d7d5 f1d3 f8e8 e1g1 d5c4 d3c4 e6e5 c4b5 e5d4 e3d4 c8d7 c1g5 h7h6 g5f6 d8f6 d4d5 c6e5 b5d7 e5d7 c3c4 d7c5 f1d1 c5e4 f3d4 e4c5 a2a3 a7a6 h2h3 c5e4 a1a2 c7c5 d5c6 b7c6 c4c5 a8d8 c2c4 e8e5 a2e2 e5c5 c4b4 c5d5 e2e4 c6c5 b4b3 c5d4 b3d3 d5d7 g1f1 d7d5 f1g1 d5d6 g1f1 g8f8 f1g1 f8g8 g1f1 d6d7 d1d2 d7d5 f1g1 d5d7 g1h2 g8f8 h2g1 f8g8 g1h2 a6a5 h2g1 f6g6 e4e1 g6d3 d2d3 a5a4 e1b1 d7c7 b1b4 c7c1 g1h2 c1c3 d3c3 d4c3 b4c4 d8d2 c4c3 d2f2 c3c8 g8h7 c8c4 f7f5 c4a4 f2a2 h2g1 a2a1 g1h2 h7g8 a4a8 g8h7 a3a4 f5f4 a4a5 a1a4 a5a6 a4a1 a6a7 g7g5 g2g4 h7g7 h2g2 a1a2 g2f1 f4f3 f1e1 g7h7 e1f1 h7g7 f1e1 g7h7 e1d1 f3f2 a8f8 a2a7 f8f2 a7a1 d1e2 h7g8 e2f3 a1a3 f3g2 g8g7 f2b2 a3a8 g2f2 a8a3 f2g2 a3a8 g2g3 a8c8 g3f2 c8c7 f2g3 g7f8 g3h2 f8g8 h2g3 c7c8 g3f2 c8d8 f2g3 d8d7 g3f2 g8f8 f2e3 d7f7 b2f2 f7f2 e3f2 f8e8 f2e3 e8d8 e3e4 d8e8 e4d5 e8f8 d5c6 f8e8 c6b6 e8d8 b6c6 d8e8 c6d6 e8f8 d6d7 f8g8 d7c8 g8f8 c8d8 f8f7 d8d7 f7f8 d7d8 f8f7 d8d7 f7g8 d7e8 g8g7 e8e7 g7g6 e7e8 g6g7 e8e7 g7g6 e7d7 g6f7 d7d6 f7g6 d6c7 h6h5 c7c6 h5g4 h3g4 g6f7 c6d7 f7f6 d7d6 f6f7 d6e5 f7g6 e5e6 g6g7 e6f5 g7h6 f5f6 h6h7 f6g5 h7g7 g5f5 g7f8 f5f6 f8e8 g4g5 e8d8 g5g6 d8c8 g6g7 c8b8 g7g8q b8b7 f6f5 b7c7 f5f6 c7b7 f6f5 b7c7 f5f6 c7b6 g8g5 b6b7 g5g8 b7c7 f6e7 c7c6 e7f6 c6c7 f6e7 c7b7 e7f6 b7a7 f6f5 a7a6 g8d5 a6b6 f5g6 b6c7 g6f5 c7b6 f5g6 b6c7 g6f5 c7b6".split(" ");
        for(String move: moves) {
            board.makeMove(move);
            if(board.isDraw())
                assertNotSame(board.zobristHashing.getHash(), board.zobristHashing.getTranspositionHash());
            else
                assertEquals(board.zobristHashing.getHash(), board.zobristHashing.getTranspositionHash());
        }

        for(int i=0; i<moves.length; i++) {
            board.unmakeMove();
            if(board.isDraw())
                assertNotSame(board.zobristHashing.getHash(), board.zobristHashing.getTranspositionHash());
            else
                assertEquals(board.zobristHashing.getHash(), board.zobristHashing.getTranspositionHash());
        }
    }

    private void perft(int depth) {

        String[] fens = board.boardToLibraryFEN().split(" ");
        String fen = fens[0] + fens[1];
        Long hash = board.zobristHashing.getHash();

        if(testForCollision.get(hash) == null)
            testForCollision.put(hash, fen);
        else{
            if(!testForCollision.get(hash).equals(fen))
                System.out.println("COLLISION: " + testForCollision.get(hash) + " " + fen);
        }

        if(depth == 0)
            return;

        ArrayList<Move> moves = generator.generateAllLegalMoves();
        for(Move move : moves) {
            long firstHash = board.zobristHashing.getHash();
            board.makeMove(move);
            perft(depth-1);
            board.unmakeMove();
            assert (firstHash == board.zobristHashing.getHash());
        }
    }
}
