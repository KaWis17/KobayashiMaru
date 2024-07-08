package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.BoardFormats.ArrayRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BitBoardsRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.CountRepresentation;
import org.example.Engine.BoardRepresentation.FEN.FenImplementer;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveMaker;
import org.example.Engine.BoardRepresentation.State.State;

import java.util.Stack;

public class Board implements BoardHelper {

    public State currentBoardState;
    public Stack<State> stateHistory = new Stack<>();

    public BitBoardsRepresentation bitBoardsRepresentation = new BitBoardsRepresentation();
    public ArrayRepresentation arrayRepresentation = new ArrayRepresentation();
    public CountRepresentation countRepresentation = new CountRepresentation();

    MoveMaker moveMaker = new MoveMaker(this);

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardHelper.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        FenImplementer.FENToBoard(this, fen);
    }

    public void makeMove(Move moveToMake) {
        moveMaker.makeMove(moveToMake);
    }

    public void makeMove(String moveToMake) {
        moveMaker.makeMove(moveToMake);
    }

    public void unmakeMove() {
        moveMaker.unmakeMove();
    }

    public void addPieceOnSquare(short square, short color, short piece) {
        bitBoardsRepresentation.addPieceOnSquare(square, color, piece);
        arrayRepresentation.addPieceOnSquare(square, color, piece);
        countRepresentation.addPieceOnSquare(square, color, piece);
    }

    public void deletePieceFromSquare(short square, short color, short piece) {
        bitBoardsRepresentation.deletePieceOnSquare(square, color, piece);
        arrayRepresentation.deletePieceOnSquare(square, color, piece);
        countRepresentation.deletePieceOnSquare(square, color, piece);
    }

    public void clearBoard() {
        bitBoardsRepresentation.clearBoard();
        arrayRepresentation.clearBoard();
        countRepresentation.clearBoard();
    }

    public short getPieceOnSquare(short square) {
        return arrayRepresentation.getPieceOnSquare(square);
    }

    public short getEnPassantTarget() {
        return currentBoardState.enPassantTarget;
    }

    public long getSpecificPiecesBitBoard(short piece) {
        return bitBoardsRepresentation.bitBoards[piece];
    }

    public int getCurrentMoveNumber() {
        return currentBoardState.fullMoveNumber;
    }

    public boolean isWhiteToPlay() {
        return currentBoardState.whiteToMove;
    }

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("BOARD: ").append(FenImplementer.BoardToFEN(this)).append("\n");

        for(short i=64; i>=1; --i) {
            switch(arrayRepresentation.getPieceOnSquare(i)) {
                case WHITE | PAWN -> sb.append("P ");
                case BLACK | PAWN -> sb.append("p ");
                case WHITE | BISHOP -> sb.append("B ");
                case BLACK | BISHOP -> sb.append("b ");
                case WHITE | KNIGHT -> sb.append("N ");
                case BLACK | KNIGHT -> sb.append("n ");
                case WHITE | ROOK -> sb.append("R ");
                case BLACK | ROOK -> sb.append("r ");
                case WHITE | QUEEN -> sb.append("Q ");
                case BLACK | QUEEN -> sb.append("q ");
                case WHITE | KING -> sb.append("K ");
                case BLACK | KING -> sb.append("k ");
                default -> sb.append("_ ");
            }

            if((i-1) % 8 == 0) sb.append("\n");
        }
        return sb.toString();
    }
}