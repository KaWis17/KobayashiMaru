package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.Move.Move;

public class State {

    Move previousMove;
    byte capturedPiece;
    boolean canWhiteCastleKingside, canWhiteCastleQueenside;
    boolean canBlackCastleKingside, canBlackCastleQueenside;
    short enPassantTarget;
    short halfMoveClock;
    short fullMoveNumber;

    public State(Board board, Move move, byte capturedPiece) {
        this.previousMove = move;
        this.capturedPiece = capturedPiece;
        this.canWhiteCastleKingside = board.canWhiteCastleKingside;
        this.canWhiteCastleQueenside = board.canWhiteCastleQueenside;
        this.canBlackCastleKingside = board.canBlackCastleKingside;
        this.canBlackCastleQueenside = board.canBlackCastleQueenside;
        this.enPassantTarget = board.enPassantTarget;
        this.halfMoveClock = board.halfMoveClock;
        this.fullMoveNumber = board.fullMoveNumber;
    }
}
