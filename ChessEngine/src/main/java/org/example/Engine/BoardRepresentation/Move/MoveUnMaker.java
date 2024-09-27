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
        String shortFen = BoardHelper.BoardToLibraryFEN(board);
        Integer value = board.positionCount.get(shortFen);
        if(value == 1)
            board.positionCount.remove(shortFen);
        else
            board.positionCount.replace(shortFen, value, value - 1);

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

        byte capturedPiece = currentState.capturedPiece;
        byte typeOfCapturedPiece = BoardHelper.getPieceType(capturedPiece);

        switch(moveToUnmake.type) {
            case QUIET_MOVE, DOUBLE_PAWN_PUSH -> unMakeQuietMove(moveToUnmake, color, piece);
            case KING_CASTLE -> unMakeKingCastleMove(color);
            case QUEEN_CASTLE -> unMakeQueenCastleMove(color);
            case CAPTURES -> unMakeCaptureMove(moveToUnmake, color, piece, typeOfCapturedPiece);
            case EP_CAPTURE -> unMakeEpCaptureMove(moveToUnmake, color, piece);
            case KNIGHT_PROMOTION -> unMakePromotionMove(moveToUnmake, color, KNIGHT);
            case KNIGHT_PROMOTION_CAPTURE -> unMakePromotionWithCaptureMove(moveToUnmake, color, KNIGHT, typeOfCapturedPiece);
            case BISHOP_PROMOTION -> unMakePromotionMove(moveToUnmake, color, BISHOP);
            case BISHOP_PROMOTION_CAPTURE -> unMakePromotionWithCaptureMove(moveToUnmake, color, BISHOP, typeOfCapturedPiece);
            case ROOK_PROMOTION -> unMakePromotionMove(moveToUnmake, color, ROOK);
            case ROOK_PROMOTION_CAPTURE -> unMakePromotionWithCaptureMove(moveToUnmake, color, ROOK, typeOfCapturedPiece);
            case QUEEN_PROMOTION -> unMakePromotionMove(moveToUnmake, color, QUEEN);
            case QUEEN_PROMOTION_CAPTURE -> unMakePromotionWithCaptureMove(moveToUnmake, color, QUEEN, typeOfCapturedPiece);
        }

    }

    private void unMakeQuietMove(Move moveToUnmake, byte color, byte piece) {
        board.deletePieceFromSquare(moveToUnmake.destination, color, piece);
        board.addPieceOnSquare(moveToUnmake.departure, color, piece);
    }

    private void unMakeCaptureMove(Move moveToUnmake, byte color, byte piece, byte typeOfCapturedPiece) {
        byte opponentColor = color == WHITE ? BLACK : WHITE;

        unMakeQuietMove(moveToUnmake, color, piece);
        board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfCapturedPiece);
    }

    private void unMakeKingCastleMove(byte color) {
        if(color == WHITE) unMakeWhiteKingCastleMove();
        else unMakeBlackKingCastleMove();
    }

    private void unMakeQueenCastleMove(byte color) {
        if(color == WHITE) unMakeWhiteQueenCastleMove();
        else unMakeBlackQueenCastleMove();
    }

    private void unMakeWhiteKingCastleMove() {
        board.deletePieceFromSquare((byte) 2, WHITE, KING);
        board.addPieceOnSquare((byte) 4, WHITE, KING);

        board.deletePieceFromSquare((byte) 3, WHITE, ROOK);
        board.addPieceOnSquare((byte) 1, WHITE, ROOK);
    }

    private void unMakeBlackKingCastleMove() {
        board.deletePieceFromSquare((byte) 58, BLACK, KING);
        board.addPieceOnSquare((byte) 60, BLACK, KING);

        board.deletePieceFromSquare((byte) 59, BLACK, ROOK);
        board.addPieceOnSquare((byte) 57, BLACK, ROOK);
    }

    private void unMakeBlackQueenCastleMove() {
        board.deletePieceFromSquare((byte) 62, BLACK, KING);
        board.addPieceOnSquare((byte) 60, BLACK, KING);

        board.deletePieceFromSquare((byte) 61, BLACK, ROOK);
        board.addPieceOnSquare((byte) 64, BLACK, ROOK);
    }

    private void unMakeWhiteQueenCastleMove() {
        board.deletePieceFromSquare((byte) 6, WHITE, KING);
        board.addPieceOnSquare((byte) 4, WHITE, KING);

        board.deletePieceFromSquare((byte) 5, WHITE, ROOK);
        board.addPieceOnSquare((byte) 8, WHITE, ROOK);
    }

    private void unMakeEpCaptureMove(Move moveToUnmake, byte color, byte piece) {
        byte opponentColor = color == WHITE ? BLACK : WHITE;

        unMakeQuietMove(moveToUnmake, color, piece);

        byte deletedPawnIndex = (color == WHITE) ? (byte) (moveToUnmake.destination - 8) : (byte) (moveToUnmake.destination + 8);
        board.addPieceOnSquare(deletedPawnIndex, opponentColor, PAWN);
    }

    private void unMakePromotionMove(Move moveToUnmake, byte color, byte pieceToDelete) {
        board.deletePieceFromSquare(moveToUnmake.destination, color, pieceToDelete);
        board.addPieceOnSquare(moveToUnmake.departure, color, PAWN);
    }

    private void unMakePromotionWithCaptureMove(Move moveToUnmake, byte color, byte pieceToDelete, byte typeOfPieceOnDestination) {
        byte opponentColor = (color == WHITE) ? BLACK : WHITE;

        unMakePromotionMove(moveToUnmake, color, pieceToDelete);
        board.addPieceOnSquare(moveToUnmake.destination, opponentColor, typeOfPieceOnDestination);
    }

}
