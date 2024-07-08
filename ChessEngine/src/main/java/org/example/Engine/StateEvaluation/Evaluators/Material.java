package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.StateEvaluation.Evaluation;

public class Material implements Evaluation, BoardHelper {

    Board board;

    public Material(Board board){
        this.board = board;
    }

    @Override
    public int evaluate() {
        return evaluateColor(WHITE) - evaluateColor(BLACK);
    }

    private int evaluateColor(short color) {
        int value = 0;

        value += 100 * board.countRepresentation.pieces[color | PAWN];
        value += 300 * board.countRepresentation.pieces[color | KNIGHT];
        value += 300 * board.countRepresentation.pieces[color | BISHOP];
        value += 500 * board.countRepresentation.pieces[color | ROOK];
        value += 900 * board.countRepresentation.pieces[color | QUEEN];

        return value;
    }

}
