package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.StateEvaluation.Evaluation;

public class Material implements Evaluation {

    Board board;

    public Material(Board board){
        this.board = board;
    }

    @Override
    public int evaluate() {
        return evaluateColor(board.WHITE) - evaluateColor(board.BLACK);
    }

    private int evaluateColor(byte color) {
        int value = 0;

        value += 100 * board.getPieceCount(color | board.PAWN);
        value += 300 * board.getPieceCount(color | board.KNIGHT);
        value += 300 * board.getPieceCount(color | board.BISHOP);
        value += 500 * board.getPieceCount(color | board.ROOK);
        value += 900 * board.getPieceCount(color | board.QUEEN);

        return value;
    }

}
