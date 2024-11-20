package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.StateEvaluation.Evaluation;

public class KingSafety implements Evaluation {
    Board board;
    int weight;

    public KingSafety(Board board, int weight){
        this.board = board;
        this.weight = weight;
    }

    @Override
    public int evaluate() {
        if(!Config.KING_SAFETY_ON)
            return 0;

        return weight * (evaluateColor(board.WHITE) - evaluateColor(board.BLACK));
    }

    private int evaluateColor(byte color) {
        int evaluation = 0;

        long kingBoard = board.getSpecificBitBoard((byte) (color | BoardHelper.KING));
        byte index = (byte) (Long.numberOfTrailingZeros(kingBoard) + 1);

        int[] directions = {-9, -8, -7, -1, 1, 7, 8, 9};
        for (int direction : directions) {
            byte adjacentIndex = (byte) (index + direction);

            if (adjacentIndex >= 1 && adjacentIndex <= 64) {
                if ((index % 8 == 1 && (direction == -9 || direction == -1 || direction == 7)) || // Left edge
                        (index % 8 == 0 && (direction == -7 || direction == 1 || direction == 9)) ||  // Right edge
                        (index <= 8 && (direction == -9 || direction == -8 || direction == -7)) ||    // Top edge
                        (index >= 57 && (direction == 7 || direction == 8 || direction == 9))) {      // Bottom edge
                    continue;
                }
                byte piece = board.getPieceOnSquare(adjacentIndex);

                byte pieceType = board.getPieceType(piece);

                if(Board.getPieceColor(piece) == color)
                    evaluation += pieceType;
                else
                    evaluation -= pieceType;
            }

        }
        return evaluation;
    }
}