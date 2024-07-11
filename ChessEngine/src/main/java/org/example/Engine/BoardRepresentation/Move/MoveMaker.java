package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.State;

public class MoveMaker implements BoardHelper, MoveConstants {

    Board board;
    public MoveMaker(Board board) {
        this.board = board;
    }

    public void makeMove(String moveToMake) {
        Move move = new Move(moveToMake, board);
        makeMove(move);
    }

    public void makeMove(Move moveToMake) {
        short pieceToMove = board.getPieceOnSquare(moveToMake.departure);
        short color = BoardHelper.getPieceColor(pieceToMove);
        short piece = BoardHelper.getPieceType(pieceToMove);

        short capturedPiece = board.getPieceOnSquare(moveToMake.destination);

        addCurrentStateToMoveHistory();

        updateBoardRepresentationsAfterMove(moveToMake, color, piece);
        createNewCurrentState(moveToMake, color, piece, capturedPiece);
    }

    private void addCurrentStateToMoveHistory() {
        board.stateHistory.add(board.currentBoardState);
    }

    private void createNewCurrentState(Move move, short color, short piece, short capturedPiece) {
        State updatedState = new State(board.currentBoardState);

        updateWhiteToMove(updatedState);

        updatedState.capturedPiece = capturedPiece;

        updateCastlingRights(move, color, piece, updatedState);

        updateEnPassantTarget(move, color, updatedState);
        updateHalfMoveClock(move, piece, updatedState);
        updateFullMoveNumber(updatedState);

        updatedState.moveThatTookToThisPosition = move;

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

    private static void updateEnPassantTarget(Move move, short color, State updatedState) {
        if(move.type == DOUBLE_PAWN_PUSH) {
            if(color == WHITE) updatedState.enPassantTarget = (short) (move.destination - 8);
            else updatedState.enPassantTarget = (short) (move.destination + 8);
        }
        else
            updatedState.enPassantTarget = 0;
    }

    private static void updateCastlingRights(Move move, short color, short piece, State updatedState) {
        updateWhiteKingsideCastleRights(move, color, piece, updatedState);

        updateWhiteQueensideCastleRights(move, color, piece, updatedState);

        updateBlackKingsideCastleRights(move, color, piece, updatedState);

        updateBlackQueensideCastleRights(move, color, piece, updatedState);
    }

    private static void updateBlackQueensideCastleRights(Move move, short color, short piece, State updatedState) {
        if(updatedState.canBlackCastleQueenside) {
            if(color == BLACK && piece == KING)
                updatedState.canBlackCastleQueenside = false;
            if(color == BLACK && piece == ROOK && move.departure == 64)
                updatedState.canBlackCastleQueenside = false;
            if(move.destination == 64)
                updatedState.canBlackCastleQueenside = false;
        }
    }

    private static void updateBlackKingsideCastleRights(Move move, short color, short piece, State updatedState) {
        if(updatedState.canBlackCastleKingside) {
            if(color == BLACK && piece == KING)
                updatedState.canBlackCastleKingside = false;
            if(color == BLACK && piece == ROOK && move.departure == 57)
                updatedState.canBlackCastleKingside = false;
            if(move.destination == 57)
                updatedState.canBlackCastleKingside = false;
        }
    }

    private static void updateWhiteQueensideCastleRights(Move move, short color, short piece, State updatedState) {
        if(updatedState.canWhiteCastleQueenside) {
            if(color == WHITE && piece == KING)
                updatedState.canWhiteCastleQueenside = false;
            if(color == WHITE && piece == ROOK && move.departure == 8)
                updatedState.canWhiteCastleQueenside = false;
            if(move.destination == 8)
                updatedState.canWhiteCastleQueenside = false;
        }
    }

    private static void updateWhiteKingsideCastleRights(Move move, short color, short piece, State updatedState) {
        if(updatedState.canWhiteCastleKingside) {
            if(color == WHITE && piece == KING)
                updatedState.canWhiteCastleKingside = false;
            if(color == WHITE && piece == ROOK && move.departure == 1)
                updatedState.canWhiteCastleKingside = false;
            if(move.destination == 1)
                updatedState.canWhiteCastleKingside = false;
        }
    }

    private void updateBoardRepresentationsAfterMove(Move move, short color, short piece) {
        short pieceOnDestination = board.getPieceOnSquare(move.destination);
        short typeOfPieceOnDestination = BoardHelper.getPieceType(pieceOnDestination);

        switch (move.type) {
            case QUIET_MOVE, DOUBLE_PAWN_PUSH -> makeQuietMove(move, color, piece);
            case CAPTURES -> makeCaptureMove(move, color, piece, typeOfPieceOnDestination);
            case EP_CAPTURE -> makeEpCaptureMove(move, color, piece);
            case KING_CASTLE -> makeKingCastleMove(color);
            case QUEEN_CASTLE -> makeQueenCastleMove(color);
            case KNIGHT_PROMOTION -> makePromotionMove(move, color, KNIGHT);
            case KNIGHT_PROMOTION_CAPTURE -> makePromotionWithCaptureMove(move, color, KNIGHT, typeOfPieceOnDestination);
            case BISHOP_PROMOTION -> makePromotionMove(move, color, BISHOP);
            case BISHOP_PROMOTION_CAPTURE -> makePromotionWithCaptureMove(move, color, BISHOP, typeOfPieceOnDestination);
            case ROOK_PROMOTION -> makePromotionMove(move, color, ROOK);
            case ROOK_PROMOTION_CAPTURE -> makePromotionWithCaptureMove(move, color, ROOK, typeOfPieceOnDestination);
            case QUEEN_PROMOTION -> makePromotionMove(move, color, QUEEN);
            case QUEEN_PROMOTION_CAPTURE -> makePromotionWithCaptureMove(move, color, QUEEN, typeOfPieceOnDestination);
        }
    }

    private void makeQuietMove(Move move, short color, short piece) {
        board.deletePieceFromSquare(move.departure, color, piece);
        board.addPieceOnSquare(move.destination, color, piece);
    }

    private void makeCaptureMove(Move move, short color, short piece, short typeOfPieceOnDestination) {
        short opponentColor = (color == WHITE) ? BLACK : WHITE;

        board.deletePieceFromSquare(move.destination, opponentColor, typeOfPieceOnDestination);
        makeQuietMove(move, color, piece);
    }

    private void makeEpCaptureMove(Move move, short color, short piece) {
        short opponentColor = (color == WHITE) ? BLACK : WHITE;

        short deletedPawnIndex = (color == WHITE) ? (short) (move.destination - 8) : (short) (move.destination + 8);
        board.deletePieceFromSquare(deletedPawnIndex, opponentColor, PAWN);

        makeQuietMove(move, color, piece);
    }

    private void makeKingCastleMove(short color) {
        if(color == WHITE) makeWhiteKingCastleMove();
        else makeBlackKingCastleMove();
    }

    private void makeWhiteKingCastleMove() {
        board.deletePieceFromSquare((short) 4, WHITE, KING);
        board.deletePieceFromSquare((short) 1, WHITE, ROOK);
        board.addPieceOnSquare((short) 2, WHITE, KING);
        board.addPieceOnSquare((short) 3, WHITE, ROOK);
    }

    private void makeBlackKingCastleMove(){
        board.deletePieceFromSquare((short) 60, BLACK, KING);
        board.deletePieceFromSquare((short) 57, BLACK, ROOK);
        board.addPieceOnSquare((short) 58, BLACK, KING);
        board.addPieceOnSquare((short) 59, BLACK, ROOK);
    }

    private void makeQueenCastleMove(short color){
        if(color == WHITE) makeWhiteQueenCastleMove();
        else makeBlackQueenCastleMove();
    }

    private void makeWhiteQueenCastleMove(){
        board.deletePieceFromSquare((short) 4, WHITE, KING);
        board.deletePieceFromSquare((short) 8, WHITE, ROOK);
        board.addPieceOnSquare((short) 6, WHITE, KING);
        board.addPieceOnSquare((short) 5, WHITE, ROOK);
    }

    private void makeBlackQueenCastleMove() {
        board.deletePieceFromSquare((short) 60, BLACK, KING);
        board.deletePieceFromSquare((short) 64, BLACK, ROOK);
        board.addPieceOnSquare((short) 62, BLACK, KING);
        board.addPieceOnSquare((short) 61, BLACK, ROOK);
    }

    private void makePromotionMove(Move move, short color, short pieceToPut) {
        board.deletePieceFromSquare(move.departure, color, PAWN);
        board.addPieceOnSquare(move.destination, color, pieceToPut);
    }

    private void makePromotionWithCaptureMove(Move move, short color, short pieceToPut, short typeOfPieceOnDestination) {
        short opponentColor = (color == WHITE) ? BLACK : WHITE;
        board.deletePieceFromSquare(move.destination, opponentColor, typeOfPieceOnDestination);
        makePromotionMove(move, color, pieceToPut);
    }
}
