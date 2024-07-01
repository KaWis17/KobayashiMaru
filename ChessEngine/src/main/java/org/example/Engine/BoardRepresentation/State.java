package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.Move.Move;

public class State {

    public boolean whiteToMove;
    public short capturedPiece;
    public boolean canWhiteCastleKingside;
    public boolean canWhiteCastleQueenside;
    public boolean canBlackCastleKingside;
    public boolean canBlackCastleQueenside;
    public short enPassantTarget;
    public short halfMoveClock;
    public short fullMoveNumber;

    public Move moveMade;
    public BoardConstants.GAME_STATE gameState;

    // Default constructor
    public State(){}

    // Copy constructor
    public State(State state) {
        this.whiteToMove = state.whiteToMove;
        this.capturedPiece = state.capturedPiece;
        this.canWhiteCastleKingside = state.canWhiteCastleKingside;
        this.canWhiteCastleQueenside = state.canWhiteCastleQueenside;
        this.canBlackCastleKingside = state.canBlackCastleKingside;
        this.canBlackCastleQueenside = state.canBlackCastleQueenside;
        this.enPassantTarget = state.enPassantTarget;
        this.halfMoveClock = state.halfMoveClock;
        this.fullMoveNumber = state.fullMoveNumber;
        this.moveMade = state.moveMade;
        this.gameState = state.gameState;
    }

    @Override
    public String toString(){
        return moveMade.toString();
    }
}
