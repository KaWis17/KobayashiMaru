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

        byte pieceToUnMove = board.getPieceOnSquare(moveToUnmake.destination);
        byte color = BoardHelper.getPieceColor(pieceToUnMove);
        byte piece = BoardHelper.getPieceType(pieceToUnMove);
        byte opponentColor = color == WHITE ? BLACK : WHITE;

        byte capturedPiece = currentState.capturedPiece;
        byte typeOfCapturedPiece = BoardHelper.getPieceType(capturedPiece);

        switch(moveToUnmake.type) {
            case QUIET_MOVE, DOUBLE_PAWN_PUSH -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);
            }
            case KING_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((byte) 2, WHITE, KING);
                    board.addPieceOnSquare((byte) 4, WHITE, KING);

                    board.deletePieceFromSquare((byte) 3, WHITE, ROOK);
                    board.addPieceOnSquare((byte) 1, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((byte) 58, BLACK, KING);
                    board.addPieceOnSquare((byte) 60, BLACK, KING);

                    board.deletePieceFromSquare((byte) 59, BLACK, ROOK);
                    board.addPieceOnSquare((byte) 57, BLACK, ROOK);
                }
            }
            case QUEEN_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((byte) 6, WHITE, KING);
                    board.addPieceOnSquare((byte) 4, WHITE, KING);

                    board.deletePieceFromSquare((byte) 5, WHITE, ROOK);
                    board.addPieceOnSquare((byte) 8, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((byte) 62, BLACK, KING);
                    board.addPieceOnSquare((byte) 60, BLACK, KING);

                    board.deletePieceFromSquare((byte) 61, BLACK, ROOK);
                    board.addPieceOnSquare((byte) 64, BLACK, ROOK);
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
                    board.addPieceOnSquare((byte) (moveToUnmake.destination-8), opponentColor, PAWN);
                else
                    board.addPieceOnSquare((byte) (moveToUnmake.destination+8), opponentColor, PAWN);
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
