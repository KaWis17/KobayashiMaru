package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;

import java.util.Comparator;

public class MoveComparator implements MoveConstants, BoardHelper, Comparator<Move> {

    private final Board board;

    public MoveComparator(Board board) {
        this.board = board;
    }

    @Override
    public int compare(Move move1, Move move2) {
        int priority1 = calculatePriority(move1);
        int priority2 = calculatePriority(move2);
        return Integer.compare(priority2, priority1);
    }

    private int calculatePriority(Move move) {
        switch(move.type){
            case QUIET_MOVE,
                 ROOK_PROMOTION,
                 BISHOP_PROMOTION,
                 ROOK_PROMOTION_CAPTURE,
                 BISHOP_PROMOTION_CAPTURE-> {
                return 0;
            }
            case DOUBLE_PAWN_PUSH -> {
                return 1;
            }
            case KING_CASTLE,
                 QUEEN_CASTLE -> {
                return 2;
            }
            case KNIGHT_PROMOTION -> {
                return 3;
            }
            case QUEEN_PROMOTION -> {
                return 4;
            }
            default -> {
                byte pieceToBeMoved = board.getPieceOnSquare(move.departure);
                byte pieceOnDestination = board.getPieceOnSquare(move.destination);
                int gain = getPieceValue(pieceOnDestination) - getPieceValue(pieceToBeMoved);
                return gain + 4 + 4;
            }
        }
    }

    private int getPieceValue(byte piece){
        switch(piece){
            case WHITE | PAWN, BLACK | PAWN -> {
                return 1;
            }
            case WHITE | BISHOP, BLACK | BISHOP,
                 WHITE | KNIGHT, BLACK | KNIGHT -> {
                return 3;
            }
            case WHITE | ROOK, BLACK | ROOK -> {
                return 5;
            }
            case WHITE | QUEEN, BLACK | QUEEN -> {
                return 9;
            }
            default -> {
                return 0;
            }
        }
    }
}