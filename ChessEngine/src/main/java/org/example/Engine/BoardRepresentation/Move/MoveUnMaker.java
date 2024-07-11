package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.State;

public class MoveUnMaker implements MoveConstants, BoardHelper{

    Board board;
    public MoveUnMaker(Board board) {
        this.board = board;
    }

    public void unmakeMove() {
        revertBoardRepresentation(board.currentBoardState);
        revertStateChange(board.stateHistory.pop());
    }

    private void revertStateChange(State stateToRestore) {
        board.currentBoardState = stateToRestore;
    }

    private void revertBoardRepresentation(State currentState) {
        Move moveToUnmake = currentState.moveThatTookToThisPosition;

        short pieceToUnMove = board.getPieceOnSquare(moveToUnmake.destination);
        short color = BoardHelper.getPieceColor(pieceToUnMove);
        short piece = BoardHelper.getPieceType(pieceToUnMove);
        short opponentColor = color == WHITE ? BLACK : WHITE;

        short capturedPiece = currentState.capturedPiece;
        short typeOfCapturedPiece = BoardHelper.getPieceType(capturedPiece);

        switch(moveToUnmake.type) {
            case QUIET_MOVE, DOUBLE_PAWN_PUSH -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);
            }
            case KING_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((short) 2, WHITE, KING);
                    board.addPieceOnSquare((short) 4, WHITE, KING);

                    board.deletePieceFromSquare((short) 3, WHITE, ROOK);
                    board.addPieceOnSquare((short) 1, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((short) 58, BLACK, KING);
                    board.addPieceOnSquare((short) 60, BLACK, KING);

                    board.deletePieceFromSquare((short) 59, BLACK, ROOK);
                    board.addPieceOnSquare((short) 57, BLACK, ROOK);
                }
            }
            case QUEEN_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((short) 6, WHITE, KING);
                    board.addPieceOnSquare((short) 4, WHITE, KING);

                    board.deletePieceFromSquare((short) 5, WHITE, ROOK);
                    board.addPieceOnSquare((short) 8, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((short) 62, BLACK, KING);
                    board.addPieceOnSquare((short) 60, BLACK, KING);

                    board.deletePieceFromSquare((short) 61, BLACK, ROOK);
                    board.addPieceOnSquare((short) 64, BLACK, ROOK);
                }
            }
            case CAPTURES -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);

                board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
            }
            case EP_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);

                if(color == WHITE)
                    board.addPieceOnSquare((short) (moveToUnmake.destination-8), opponentColor, PAWN);
                else
                    board.addPieceOnSquare((short) (moveToUnmake.destination+8), opponentColor, PAWN);
            }
            case KNIGHT_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, KNIGHT);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case KNIGHT_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, KNIGHT);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
            }
            case BISHOP_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, BISHOP);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case BISHOP_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, BISHOP);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
            }
            case ROOK_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, ROOK);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case ROOK_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, ROOK);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
            }
            case QUEEN_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, QUEEN);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case QUEEN_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, QUEEN);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
            }

        }

    }

}
