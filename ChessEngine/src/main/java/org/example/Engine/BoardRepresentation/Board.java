package org.example.Engine.BoardRepresentation;

public class Board implements BoardConstants{

    public BoardConstants.GAME_STATE state;
    long[] bitBoards = new long[14];
    boolean currentToMove;
    boolean canWhiteCastleKingside, canWhiteCastleQueenside;
    boolean canBlackCastleKingside, canBlackCastleQueenside;
    SquareCalculator enPassantTarget;
    short halfMoveClock;
    short fullMoveNumber;

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardConstants.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        FenImplementer.implement(this, fen);
    }

    public void makeMove(Move moveToMake, boolean saveMove) {

    }

    public void display() {

    }
}
