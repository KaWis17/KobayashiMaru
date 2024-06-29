package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.State;

public class MoveMaker implements BoardConstants, MoveConstants {

    Board board;
    public MoveMaker(Board board) {
        this.board = board;
    }

    public void makeMove(Move moveToMake) {
        short pieceToMove = board.getPieceOnSquare(moveToMake.departure);
        short color = BoardConstants.getColor(pieceToMove);
        short piece = BoardConstants.getPieceType(pieceToMove);

        addCurrentStateToMoveHistory(moveToMake);
        createNewCurrentState(moveToMake, color, piece);
        updateBoardRepresentationsAfterMove(moveToMake, color, piece);
    }

    public void unmakeMove() {
        State stateToRestore = board.stateHistory.pop();

        revertStateChange(stateToRestore);
        revertBoardRepresentation(stateToRestore);
    }

    private void revertBoardRepresentation(State stateToRestore) {
        Move moveToUnmake = stateToRestore.moveMade;

        short pieceToUnMove = board.getPieceOnSquare(moveToUnmake.destination);
        short color = BoardConstants.getColor(pieceToUnMove);
        short piece = BoardConstants.getPieceType(pieceToUnMove);
        short opponentColor = color == WHITE ? BLACK : WHITE;

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

                    board.addPieceOnSquare((short) 57, BLACK, ROOK);
                    board.deletePieceFromSquare((short) 59, BLACK, ROOK);
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

                    board.addPieceOnSquare((short) 61, BLACK, ROOK);
                    board.deletePieceFromSquare((short) 64, BLACK, ROOK);
                }
            }
            case CAPTURES -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);

                board.addPieceOnSquare(moveToUnmake.destination, BoardConstants.getColor(stateToRestore.capturedPiece), BoardConstants.getPieceType(stateToRestore.capturedPiece));
            }
            case EP_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
                board.addPieceOnSquare(moveToUnmake.departure, color, piece);

                short capturedPawnSquare = board.currentBoardState.enPassantTarget;
                board.addPieceOnSquare(capturedPawnSquare, PAWN, opponentColor);
            }
            case KNIGHT_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, KNIGHT);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case KNIGHT_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, KNIGHT);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, BoardConstants.getColor(stateToRestore.capturedPiece), BoardConstants.getPieceType(stateToRestore.capturedPiece));
            }
            case BISHOP_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, BISHOP);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case BISHOP_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, BISHOP);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, BoardConstants.getColor(stateToRestore.capturedPiece), BoardConstants.getPieceType(stateToRestore.capturedPiece));
            }
            case ROOK_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, ROOK);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case ROOK_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, ROOK);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, BoardConstants.getColor(stateToRestore.capturedPiece), BoardConstants.getPieceType(stateToRestore.capturedPiece));
            }
            case QUEEN_PROMOTION -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, QUEEN);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
            }
            case QUEEN_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(moveToUnmake.destination, color, QUEEN);
                board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);

                board.addPieceOnSquare(moveToUnmake.destination, BoardConstants.getColor(stateToRestore.capturedPiece), BoardConstants.getPieceType(stateToRestore.capturedPiece));
            }

        }

    }

    private void revertStateChange(State stateToRestore) {
        board.currentBoardState = stateToRestore;
    }

    private void addCurrentStateToMoveHistory(Move move) {
        State currentState = board.currentBoardState;
        currentState.capturedPiece = board.getPieceOnSquare(move.destination);
        currentState.moveMade = move;

        board.stateHistory.add(currentState);
    }

    private void createNewCurrentState(Move move, short color, short piece) {
        State updatedState = new State(board.currentBoardState);
        updatedState.moveMade = null;

        updateWhiteToMove(updatedState);
        updateCastlingRights(move, color, piece, updatedState);
        updateEnPassantTarget(move, color, piece, updatedState);
        updateHalfMoveClock(move, piece, updatedState);
        updateFullMoveNumber(updatedState);

        board.currentBoardState = updatedState;
    }

    private static void updateWhiteToMove(State updatedState) {
        updatedState.whiteToMove = !updatedState.whiteToMove;
    }

    private void updateFullMoveNumber(State updatedState) {
        if(updatedState.whiteToMove)
            updatedState.fullMoveNumber = (short) (board.currentBoardState.fullMoveNumber + 1);
        else
            updatedState.fullMoveNumber = board.currentBoardState.fullMoveNumber;
    }

    private void updateHalfMoveClock(Move move, short piece, State updatedState) {
        short pieceOnDestination = board.getPieceOnSquare(move.destination);

        if(piece == PAWN || pieceOnDestination != 0)
            updatedState.halfMoveClock = 0;
        else
            updatedState.halfMoveClock = (short) (board.currentBoardState.halfMoveClock + 1);
    }

    private static void updateEnPassantTarget(Move move, short color, short piece, State updatedState) {
        if(move.type == DOUBLE_PAWN_PUSH) {
            if(color == WHITE) updatedState.enPassantTarget = (short) (move.destination - 8);
            else updatedState.enPassantTarget = (short) (move.destination + 8);
        }
        else
            updatedState.enPassantTarget = 0;
    }

    private static void updateCastlingRights(Move move, short color, short piece, State updatedState) {
        if(updatedState.canWhiteCastleKingside) {
            if(color == WHITE && piece == KING)
                updatedState.canWhiteCastleKingside = false;
            if(color == WHITE && piece == ROOK && move.departure == 8)
                updatedState.canWhiteCastleKingside = false;
        }

        if(updatedState.canWhiteCastleQueenside) {
            if(color == WHITE && piece == KING)
                updatedState.canWhiteCastleQueenside = false;
            if(color == WHITE && piece == ROOK && move.departure == 1)
                updatedState.canWhiteCastleQueenside = false;
        }

        if(updatedState.canBlackCastleKingside) {
            if(color == BLACK && piece == KING)
                updatedState.canBlackCastleKingside = false;
            if(color == BLACK && piece == ROOK && move.departure == 64)
                updatedState.canBlackCastleKingside = false;
        }

        if(updatedState.canBlackCastleQueenside) {
            if(color == BLACK && piece == KING)
                updatedState.canBlackCastleQueenside = false;
            if(color == BLACK && piece == ROOK && move.departure == 57)
                updatedState.canBlackCastleQueenside = false;
        }
    }

    private void updateBoardRepresentationsAfterMove(Move move, short color, short piece) {
        board.deletePieceFromSquare(move.departure, color, piece);

        short opponentColor = color == WHITE ? BLACK : WHITE;
        switch (move.type) {
            case QUIET_MOVE, DOUBLE_PAWN_PUSH -> {
                board.addPieceOnSquare(move.destination, color, piece);
            }
            case CAPTURES -> {
                board.deletePieceFromSquare(move.destination, opponentColor, BoardConstants.getPieceType(board.getPieceOnSquare(move.destination)));
                board.addPieceOnSquare(move.destination, color, piece);
            }
            case EP_CAPTURE -> {
                short capturedPiece = board.stateHistory.peek().enPassantTarget;
                board.deletePieceFromSquare(capturedPiece, PAWN, opponentColor);
            }
            case KING_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((short) 1, WHITE, ROOK);
                    board.addPieceOnSquare((short) 2, WHITE, KING);
                    board.addPieceOnSquare((short) 3, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((short) 57, BLACK, ROOK);
                    board.addPieceOnSquare((short) 58, BLACK, KING);
                    board.addPieceOnSquare((short) 59, BLACK, ROOK);
                }
            }
            case QUEEN_CASTLE -> {
                if(color == WHITE) {
                    board.deletePieceFromSquare((short) 8, WHITE, ROOK);
                    board.addPieceOnSquare((short) 6, WHITE, KING);
                    board.addPieceOnSquare((short) 5, WHITE, ROOK);
                }
                else {
                    board.deletePieceFromSquare((short) 64, BLACK, ROOK);
                    board.addPieceOnSquare((short) 62, BLACK, KING);
                    board.addPieceOnSquare((short) 61, BLACK, ROOK);
                }
            }
            case KNIGHT_PROMOTION -> {
                board.addPieceOnSquare(move.destination, color, KNIGHT);
            }
            case KNIGHT_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(move.destination, BoardConstants.getColor(board.getPieceOnSquare(move.destination)), BoardConstants.getPieceType(board.getPieceOnSquare(move.destination)));
                board.addPieceOnSquare(move.destination, color, KNIGHT);
            }
            case BISHOP_PROMOTION, BISHOP_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(move.destination, BoardConstants.getColor(board.getPieceOnSquare(move.destination)), BoardConstants.getPieceType(board.getPieceOnSquare(move.destination)));
                board.addPieceOnSquare(move.destination, color, BISHOP);
            }
            case ROOK_PROMOTION, ROOK_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(move.destination, BoardConstants.getColor(board.getPieceOnSquare(move.destination)), BoardConstants.getPieceType(board.getPieceOnSquare(move.destination)));
                board.addPieceOnSquare(move.destination, color, ROOK);
            }
            case QUEEN_PROMOTION, QUEEN_PROMOTION_CAPTURE -> {
                board.deletePieceFromSquare(move.destination, BoardConstants.getColor(board.getPieceOnSquare(move.destination)), BoardConstants.getPieceType(board.getPieceOnSquare(move.destination)));
                board.addPieceOnSquare(move.destination, color, QUEEN);
            }
        }

    }
}
