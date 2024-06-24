package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.BoardFormats.ArrayRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BitBoardsRepresentation;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.Stack;

public class Board implements BoardConstants {

    public BoardConstants.GAME_STATE state;
    public BitBoardsRepresentation bitBoards = new BitBoardsRepresentation();
    ArrayRepresentation arrayRepresentation = new ArrayRepresentation();

    Stack<State> stateHistory = new Stack<>();

    public boolean whiteToMove;
    boolean canWhiteCastleKingside, canWhiteCastleQueenside;
    boolean canBlackCastleKingside, canBlackCastleQueenside;
    short enPassantTarget;
    short halfMoveClock;
    short fullMoveNumber;

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardConstants.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        FenImplementer.implement(this, fen);
    }

    public void makeMove(Move moveToMake) {
        // Update state history, including piece captured on destination square
        byte pieceOnDestination = arrayRepresentation.getPieceOnSquare(moveToMake.destination);
        stateHistory.add(new State(this, moveToMake, pieceOnDestination));

        // Get info about piece to move
        short pieceOnSquare = arrayRepresentation.getPieceOnSquare(moveToMake.departure);
        short color = BoardConstants.getColor(pieceOnSquare);
        short piece = BoardConstants.getPieceType(pieceOnSquare);

        // Update board representation
        deletePieceFromSquare(moveToMake.departure, color, piece);
        addPieceOnSquare(moveToMake.destination, color, piece);

        // Update castling rights
        if(color == WHITE) {
            if(piece == KING) {
                canWhiteCastleKingside = false;
                canWhiteCastleQueenside = false;
            } else if(piece == ROOK) {
                if(moveToMake.departure == 1) canWhiteCastleQueenside = false;
                else if(moveToMake.departure == 8) canWhiteCastleKingside = false;
            }
        } else {
            if(piece == KING) {
                canBlackCastleKingside = false;
                canBlackCastleQueenside = false;
            } else if(piece == ROOK) {
                if(moveToMake.departure == 57) canBlackCastleQueenside = false;
                else if(moveToMake.departure == 64) canBlackCastleKingside = false;
            }
        }

        // Update en passant target square
        if(piece == PAWN && Math.abs(moveToMake.destination - moveToMake.departure) == 16) {
            if(color == WHITE) enPassantTarget = (short) (moveToMake.destination - 8);
            else enPassantTarget = (short) (moveToMake.destination + 8);
        } else enPassantTarget = 0;

        // Update half move clock
        if(piece == PAWN || pieceOnDestination != 0) halfMoveClock = 0;
        else halfMoveClock++;

        // Update full move number
        if(!whiteToMove) fullMoveNumber++;

        // Update whose turn it is
        whiteToMove = !whiteToMove;
    }

    public void unmakeMove() {
        State stateToRestore = stateHistory.pop();

        // Restore state
        whiteToMove = !whiteToMove;
        canWhiteCastleKingside = stateToRestore.canWhiteCastleKingside;
        canWhiteCastleQueenside = stateToRestore.canWhiteCastleQueenside;
        canBlackCastleKingside = stateToRestore.canBlackCastleKingside;
        canBlackCastleQueenside = stateToRestore.canBlackCastleQueenside;
        enPassantTarget = stateToRestore.enPassantTarget;
        halfMoveClock = stateToRestore.halfMoveClock;
        fullMoveNumber = stateToRestore.fullMoveNumber;

        // Move piece back to departure square
        Move moveToUnmake = stateToRestore.previousMove;
        short pieceOnSquare = arrayRepresentation.getPieceOnSquare(moveToUnmake.destination);
        short movedPieceColor = BoardConstants.getColor(pieceOnSquare);
        short movedPieceType = BoardConstants.getPieceType(pieceOnSquare);
        addPieceOnSquare(moveToUnmake.departure, movedPieceColor, movedPieceType);

        // Restore captured piece
        short capturedPieceColor = BoardConstants.getColor(stateToRestore.capturedPiece);
        short capturedPieceType = BoardConstants.getPieceType(stateToRestore.capturedPiece);
        addPieceOnSquare(moveToUnmake.destination, capturedPieceColor, capturedPieceType);
    }

    void addPieceOnSquare(short square, short color, short piece) {
        bitBoards.addPieceOnSquare(square, color, piece);
        arrayRepresentation.addPieceOnSquare(square, color, piece);
    }

    void deletePieceFromSquare(short square, short color, short piece) {
        bitBoards.deletePieceOnSquare(square, color, piece);
        arrayRepresentation.deletePieceOnSquare(square, color, piece);
    }

    void clearBoard() {
        bitBoards.clearBoard();
        arrayRepresentation.clearBoard();
    }

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("STATE: \n");
        if(whiteToMove) sb.append("White on the move\n");
        else sb.append("Black on the move\n");


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
