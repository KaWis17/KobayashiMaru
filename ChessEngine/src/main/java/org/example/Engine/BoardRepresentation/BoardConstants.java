package org.example.Engine.BoardRepresentation;

public interface BoardConstants {
    enum GAME_STATE {EARLY_GAME, MID_GAME, END_GAME}
    String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    short WHITE = 0;
    short BLACK = 8;

    short PAWN = 1;
    short BISHOP = 2;
    short KNIGHT = 3;
    short ROOK = 4;
    short QUEEN = 5;
    short KING = 6;

    static short getPieceType(short piece) {
        return (short) (piece & 7);
    }

    static short getColor(short piece) {
        return (short) (piece & 8);
    }
}
