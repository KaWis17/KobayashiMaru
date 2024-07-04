package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class PerftTest {

    public static long perft(String fen, int depth) {

        if(depth == 0)
            return 1L;

        Board board = new Board();
        board.startFromCustomPosition(fen);

        MoveGenerator generator = new MoveGenerator(board);

        long sum = 0;
        for(Move move: generator.generateMoves()) {
            board.makeMove(move);
            long children = perftHelper(generator, board, move, depth-1);

            System.out.println(move + ": " + children);

            sum += children;

            board.unmakeMove();
        }

        return sum;
    }

    private static long perftHelper(MoveGenerator generator, Board board, Move move, int depth) {
        if(depth == 0)
            return 1L;

        long sum = 0;
        ArrayList<Move> moves = generator.generateMoves();

        for(Move newMove: moves) {
            board.makeMove(newMove);
            sum += perftHelper(generator, board, newMove, depth-1);
            board.unmakeMove();
        }

        return sum;
    }
}
