package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class PerftTest {

    public static long perft(String fen, int depth) {
        Board board = new Board();
        board.startFromCustomPosition(fen);

        return perft(board, depth);
    }

    public static long perft(Board board, int depth) {

        if(depth == 0)
            return 1L;

        MoveGenerator generator = new MoveGenerator(board);

        long sum = 0;
        for(Move move: generator.generateAllLegalMoves()) {
            board.makeMove(move);
            long children = perftHelper(generator, board, depth-1);

            System.out.println(move + ": " + children);

            sum += children;

            board.unmakeMove();
        }

        return sum;
    }

    private static long perftHelper(MoveGenerator generator, Board board, int depth) {
        if(depth == 0)
            return 1L;

        long sum = 0;
        ArrayList<Move> moves = generator.generateAllLegalMoves();

        for(Move newMove: moves) {
            board.makeMove(newMove);
            sum += perftHelper(generator, board, depth-1);
            board.unmakeMove();
        }

        return sum;
    }
}
