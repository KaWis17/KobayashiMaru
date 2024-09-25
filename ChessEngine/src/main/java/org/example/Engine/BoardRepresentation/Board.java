package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.BoardFormats.ArrayRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BitBoardsRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.CountRepresentation;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveMaker;
import org.example.Engine.BoardRepresentation.Move.MoveUnMaker;

import java.util.Stack;

public class Board implements BoardHelper {

    public State currentBoardState;
    public Stack<State> stateHistory = new Stack<>();

    public BitBoardsRepresentation bitBoardsRepresentation = new BitBoardsRepresentation();
    public ArrayRepresentation arrayRepresentation = new ArrayRepresentation();
    public CountRepresentation countRepresentation = new CountRepresentation();

    MoveMaker moveMaker = new MoveMaker(this);
    MoveUnMaker moveUnMaker = new MoveUnMaker(this);

    CheckChecker checkChecker = new CheckChecker(this);

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardHelper.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        BoardHelper.FENToBoard(this, fen);
    }

    public void makeMove(Move moveToMake) {
        moveMaker.makeMove(moveToMake);
    }

    public void makeMove(String moveToMake) {
        moveMaker.makeMove(moveToMake);
    }

    public void unmakeMove() {
        moveUnMaker.unmakeMove();
    }

    public void addPieceOnSquare(byte square, byte color, byte piece) {
        bitBoardsRepresentation.addPieceOnSquare(square, color, piece);
        arrayRepresentation.addPieceOnSquare(square, color, piece);
        countRepresentation.addPieceOnSquare(square, color, piece);
    }

    public void deletePieceFromSquare(byte square, byte color, byte piece) {
        bitBoardsRepresentation.deletePieceOnSquare(square, color, piece);
        arrayRepresentation.deletePieceOnSquare(square, color, piece);
        countRepresentation.deletePieceOnSquare(square, color, piece);
    }

    public void clearBoard() {
        bitBoardsRepresentation.clearBoard();
        arrayRepresentation.clearBoard();
        countRepresentation.clearBoard();
    }

    public byte getPieceOnSquare(byte square) {
        return arrayRepresentation.getPieceOnSquare(square);
    }

    public byte getEnPassantTarget() {
        return currentBoardState.enPassantTarget;
    }

    public long getSpecificBitBoard(byte piece) {
        return bitBoardsRepresentation.bitBoards[piece];
    }

    public int getCurrentMoveNumber() {
        return currentBoardState.fullMoveNumber;
    }

    public boolean isWhiteToPlay() {
        return currentBoardState.whiteToMove;
    }

    public boolean isOpponentColorInCheck() {
        return checkChecker.isColorInCheck(currentBoardState.whiteToMove ? BLACK : WHITE);
    }

    public boolean isWhiteInCheck() {
        return checkChecker.isWhiteInCheck();
    }

    public boolean isWhiteInCheckMate() {
        return checkChecker.isWhiteInCheckMate();
    }

    public boolean isBlackInCheck() {
        return checkChecker.isBlackInCheck();
    }

    public boolean isBlackInCheckMate() {
        return checkChecker.isBlackInCheckMate();
    }

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("BOARD: ").append(BoardHelper.BoardToFEN(this)).append("\n");;
        sb.append("isWhiteInCheck: ").append(isWhiteInCheck()).append("\n");
        sb.append("isWhiteInCheckMate: ").append(isWhiteInCheckMate()).append("\n");
        sb.append("isBlackInCheck: ").append(isBlackInCheck()).append("\n");
        sb.append("isBlackInCheckMate: ").append(isBlackInCheckMate()).append("\n");

        for(byte i=64; i>=1; --i) {
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