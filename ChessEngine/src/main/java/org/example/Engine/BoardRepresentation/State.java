package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.Move.Move;
import static org.example.Engine.BoardRepresentation.BoardHelper.*;

public class State {

    public boolean whiteToMove;

    public byte capturedPiece;

    public boolean canWhiteCastleKingSide;
    public boolean canWhiteCastleQueenSide;
    public boolean canBlackCastleKingSide;
    public boolean canBlackCastleQueenSide;

    public byte enPassantTarget;

    public byte halfMoveClock;
    public byte fullMoveNumber;

    public Move moveThatTookToThisPosition;
    public GAME_STATE gameState;

    public String FEN = "";

    // Default constructor
    public State(){}

    // Copy constructor
    public State(State state) {
        this.whiteToMove = state.whiteToMove;

        this.capturedPiece = state.capturedPiece;

        this.canWhiteCastleKingSide = state.canWhiteCastleKingSide;
        this.canWhiteCastleQueenSide = state.canWhiteCastleQueenSide;
        this.canBlackCastleKingSide = state.canBlackCastleKingSide;
        this.canBlackCastleQueenSide = state.canBlackCastleQueenSide;

        this.enPassantTarget = state.enPassantTarget;

        this.halfMoveClock = state.halfMoveClock;
        this.fullMoveNumber = state.fullMoveNumber;

        this.moveThatTookToThisPosition = state.moveThatTookToThisPosition;
        this.gameState = state.gameState;
        this.FEN = state.FEN;
    }
}