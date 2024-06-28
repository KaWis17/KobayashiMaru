package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

public class PerftTest {

    public static void perft(String fen, int depth) {
        Board board = new Board();
        board.startFromCustomPosition(fen);

        MoveGenerator generator = new MoveGenerator(board);

        int sum = 0;
        for(Move move: generator.generateMoves()) {
            int children = perftHelper(generator, board, move, depth-1);
            sum+= children;
            System.out.println(move + ": " + children);
        }
        System.out.println("TOTAL: " + sum);

        System.out.println();
    }

    private static int perftHelper(MoveGenerator generator, Board board, Move move, int depth) {
        if(depth == 0)
            return 1;

        board.makeMove(move);

        int sum = 1;

        for(Move newMove: generator.generateMoves()) {
            sum += perftHelper(generator, board, newMove, depth-1);
        }

        board.unmakeMove();
        return sum;
    }
}
