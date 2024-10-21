package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.Engine;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.MoveGeneration.PieceGenerators.Generator;

import java.util.ArrayList;
import java.util.HashMap;

public class Zobrist extends TestCase {
    HashMap<String, Long> testForCollision;
    Board board;
    MoveGenerator generator;

    public void testCase() {
        board = new Board();
        board.startFromDefaultPosition();
        generator = new MoveGenerator(board);
        testForCollision = new HashMap<>();
        perft(2);
        System.out.println("SIZE: " + testForCollision.size());
    }

    private void perft(int depth) {

        String fen = board.boardToLibraryFEN();
        Long hash = board.zobristHashing.getHash();

        if(testForCollision.get(fen) == null)
            testForCollision.put(fen, hash);
        else{
            assert (testForCollision.get(fen).equals(hash));
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
