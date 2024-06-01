package org.example.Engine.BoardRepresentation;

public interface MoveConstants {
    short QUIET_MOVE = 0;
    short DOUBLE_PAWN_PUSH = 1;
    short KING_CASTLE = 2;
    short QUEEN_CASTLE = 3;
    short CAPTURES = 4;
    short EP_CAPTURE = 5;
    short KNIGHT_PROMOTION = 8;
    short BISHOP_PROMOTION = 9;
    short ROOK_PROMOTION = 10;
    short QUEEN_PROMOTION = 11;
    short KNIGHT_PROMOTION_CAPTURE = 12;
    short BISHOP_PROMOTION_CAPTURE = 13;
    short ROOK_PROMOTION_CAPTURE = 14;
    short QUEEN_PROMOTION_CAPTURE = 15;
}
