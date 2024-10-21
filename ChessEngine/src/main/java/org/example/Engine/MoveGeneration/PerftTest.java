package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.sql.Time;
import java.util.ArrayList;

public class PerftTest {

    public static long perft(String fen, int depth) {
        Board board = new Board();
        board.startFromCustomPosition(fen);

        long start = System.currentTimeMillis();
        long value = perft(board, depth);
        System.out.println(System.currentTimeMillis() - start);
        return value;
    }

    public static long perft(Board board, int depth) {
        long start = System.currentTimeMillis();

        if(depth == 0)
            return 1L;

        MoveGenerator generator = new MoveGenerator(board);

        long sum = 0;
        for(Move move: generator.generateAllPseudoLegalMoves()) {
            if(board.makeMove(move)) {
                long children = perftHelper(generator, board, depth-1);

                System.out.println(move + ": " + children);

                sum += children;
            }
            board.unmakeMove();
        }
        System.out.println("TIME: " + (System.currentTimeMillis() - start));
        return sum;
    }

    private static long perftHelper(MoveGenerator generator, Board board, int depth) {
        if(depth == 0)
            return 1L;

        long sum = 0;
        ArrayList<Move> moves = generator.generateAllPseudoLegalMoves();

        for(Move newMove: moves) {
            if(board.makeMove(newMove)){
                sum += perftHelper(generator, board, depth-1);
            }
            board.unmakeMove();
        }

        return sum;
    }
}
