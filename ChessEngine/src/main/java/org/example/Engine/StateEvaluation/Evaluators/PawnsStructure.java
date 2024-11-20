package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluation;

public class PawnsStructure implements Evaluation {

    Board board;
    int weight;

    PawnsStructure(Board board, int weight){
        this.board = board;
        this.weight = weight;
    }

    @Override
    public int evaluate() {
        return evaluateColor(board.WHITE) - evaluateColor(board.BLACK);
    }

    private int evaluateColor(byte color) {
        byte opponentColor = color == Board.WHITE ? Board.BLACK : Board.WHITE;

        long pawns = board.getSpecificBitBoard((byte) (color | Board.PAWN));
        long opponentPawns = board.getSpecificBitBoard((byte) (opponentColor | Board.PAWN));

        int evaluation = evaluateSupport(pawns, color);
        evaluation += evaluateColumns(pawns);
        evaluation += evaluateDoubledPawns(pawns);
        evaluation += evaluatePassedPawns(pawns, opponentPawns);
        return evaluation;
    }

    private int evaluateSupport(long pawns, byte color) {
        return 0;
    }

    private int evaluateColumns(long pawns) {
        return 0;
    }

    private int evaluateDoubledPawns(long pawns) {
        return 0;
    }

    private int evaluatePassedPawns(long pawns, long opponentPawns) {
        return 0;
    }
}
