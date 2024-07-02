package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.StateEvaluation.Evaluation;

public class Material implements Evaluation, BoardConstants {

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

        value += 100 * board.pieceCountRepresentation.pieces[color | PAWN];
        value += 300 * board.pieceCountRepresentation.pieces[color | KNIGHT];
        value += 300 * board.pieceCountRepresentation.pieces[color | BISHOP];
        value += 500 * board.pieceCountRepresentation.pieces[color | ROOK];
        value += 900 * board.pieceCountRepresentation.pieces[color | QUEEN];
        value += 20000 * board.pieceCountRepresentation.pieces[color | KING];
        return value;

    }

}
