package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.BoardFormats.ArrayRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BitBoardsRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.PieceCountRepresentation;
import org.example.Engine.BoardRepresentation.FEN.FenImplementer;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveMaker;
import org.example.Engine.BoardRepresentation.State.State;

import java.util.Stack;

public class Board implements BoardConstants {

    public State currentBoardState;

    public BitBoardsRepresentation bitBoardsRepresentation = new BitBoardsRepresentation();
    public ArrayRepresentation arrayRepresentation = new ArrayRepresentation();
    public PieceCountRepresentation pieceCountRepresentation = new PieceCountRepresentation();

    MoveMaker moveMaker = new MoveMaker(this);

    public Stack<State> stateHistory = new Stack<>();

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardConstants.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        FenImplementer.FENToBoard(this, fen);
    }

    public void makeMove(Move moveToMake) {
        moveMaker.makeMove(moveToMake);
    }

    public void unmakeMove() {
        moveMaker.unmakeMove();
    }

    public void addPieceOnSquare(short square, short color, short piece) {
        bitBoardsRepresentation.addPieceOnSquare(square, color, piece);
        arrayRepresentation.addPieceOnSquare(square, color, piece);
        pieceCountRepresentation.addPieceOnSquare(square, color, piece);
    }

    public void deletePieceFromSquare(short square, short color, short piece) {
        bitBoardsRepresentation.deletePieceOnSquare(square, color, piece);
        arrayRepresentation.deletePieceOnSquare(square, color, piece);
        pieceCountRepresentation.deletePieceOnSquare(square, color, piece);
    }

    public void clearBoard() {
        bitBoardsRepresentation.clearBoard();
        arrayRepresentation.clearBoard();
        pieceCountRepresentation.clearBoard();
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

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("BOARD: " + FenImplementer.BoardToFEN(this) + "\n");

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

    public int getCurrentMoveNumber(){
        return currentBoardState.fullMoveNumber;
    }

    public boolean isWhiteToPlay(){
        return currentBoardState.whiteToMove;
    }
}