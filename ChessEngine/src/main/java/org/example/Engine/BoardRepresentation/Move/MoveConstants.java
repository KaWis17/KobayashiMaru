package org.example.Engine.BoardRepresentation.Move;

public interface MoveConstants {
    byte QUIET_MOVE = 0;
    byte DOUBLE_PAWN_PUSH = 1;
    byte KING_CASTLE = 2;
    byte QUEEN_CASTLE = 3;
    byte CAPTURES = 4;
    byte EP_CAPTURE = 5;
    byte KNIGHT_PROMOTION = 8;
    byte BISHOP_PROMOTION = 9;
    byte ROOK_PROMOTION = 10;
    byte QUEEN_PROMOTION = 11;
    byte KNIGHT_PROMOTION_CAPTURE = 12;
    byte BISHOP_PROMOTION_CAPTURE = 13;
    byte ROOK_PROMOTION_CAPTURE = 14;
    byte QUEEN_PROMOTION_CAPTURE = 15;
}
