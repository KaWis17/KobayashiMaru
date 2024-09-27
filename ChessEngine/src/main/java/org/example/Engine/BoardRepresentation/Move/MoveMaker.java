package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.CheckChecker;
import org.example.Engine.BoardRepresentation.State;

public class MoveMaker implements BoardHelper, MoveConstants {

    Board board;
    CheckChecker checkChecker;

    public MoveMaker(Board board) {
        this.board = board;
        checkChecker = new CheckChecker(board);
    }

    public void makeMove(String moveToMake) {
        Move move = new Move(moveToMake, board);
        makeMove(move);
    }

    public void makeMove(Move moveToMake) {
        byte pieceToMove = board.getPieceOnSquare(moveToMake.departure);
        byte color = BoardHelper.getPieceColor(pieceToMove);
        byte piece = BoardHelper.getPieceType(pieceToMove);

        byte capturedPiece = board.getPieceOnSquare(moveToMake.destination);

        addCurrentStateToMoveHistory();

        updateBoardRepresentationsAfterMove(moveToMake, color, piece);
        createNewCurrentState(moveToMake, color, piece, capturedPiece);

        String shortFen = BoardHelper.BoardToLibraryFEN(board);
        Integer value = board.positionCount.get(shortFen);
        if (value == null || value == 0) {
            board.positionCount.put(shortFen, 1);
        } else {
            board.positionCount.replace(shortFen, value, value + 1);
        }
    }

    private void addCurrentStateToMoveHistory() {
        board.stateHistory.add(board.currentBoardState);
    }

    private void createNewCurrentState(Move move, byte color, byte piece, byte capturedPiece) {
        State updatedState = new State(board.currentBoardState);

        updateWhiteToMove(updatedState);

        updatedState.capturedPiece = capturedPiece;

        updateCastlingRights(move, color, piece, updatedState);

        updateEnPassantTarget(move, color, updatedState);
        updateHalfMoveClock(move, piece, updatedState);
        updateFullMoveNumber(updatedState);

        updatedState.moveThatTookToThisPosition = move;
        updatedState.FEN = BoardHelper.getBoardFen(board);

        board.currentBoardState = updatedState;
    }

    private static void updateWhiteToMove(State updatedState) {
        updatedState.whiteToMove = !updatedState.whiteToMove;
    }

    private void updateFullMoveNumber(State updatedState) {
        if(updatedState.whiteToMove)
            updatedState.fullMoveNumber = (byte) (board.currentBoardState.fullMoveNumber + 1);
        else
            updatedState.fullMoveNumber = board.currentBoardState.fullMoveNumber;
    }

    private void updateHalfMoveClock(Move move, byte piece, State updatedState) {
        byte pieceOnDestination = board.getPieceOnSquare(move.destination);

        if(piece == PAWN || pieceOnDestination != 0)
            updatedState.halfMoveClock = 0;
        else
            updatedState.halfMoveClock = (byte) (board.currentBoardState.halfMoveClock + 1);
    }

    private static void updateEnPassantTarget(Move move, byte color, State updatedState) {
        if(move.type == DOUBLE_PAWN_PUSH) {
            if(color == WHITE) updatedState.enPassantTarget = (byte) (move.destination - 8);
            else updatedState.enPassantTarget = (byte) (move.destination + 8);
        }
        else
            updatedState.enPassantTarget = 0;
    }

    private static void updateCastlingRights(Move move, byte color, byte piece, State updatedState) {
        updatedState.canWhiteCastleKingside = updateWhiteKingsideCastleRights(move, color, piece, updatedState);
        updatedState.canWhiteCastleQueenside = updateWhiteQueensideCastleRights(move, color, piece, updatedState);
        updatedState.canBlackCastleKingside = updateBlackKingsideCastleRights(move, color, piece, updatedState);
        updatedState.canBlackCastleQueenside = updateBlackQueensideCastleRights(move, color, piece, updatedState);
    }

    private static boolean updateWhiteKingsideCastleRights(Move move, byte color, byte piece, State previousState) {
        if(!previousState.canWhiteCastleKingside) return false;
        if(color == WHITE && piece == KING) return false;
        if(color == WHITE && piece == ROOK && move.departure == 1) return false;
        return move.destination != 1;
    }

    private static boolean updateWhiteQueensideCastleRights(Move move, byte color, byte piece, State previousState) {
        if(!previousState.canWhiteCastleQueenside) return false;
        if(color == WHITE && piece == KING) return false;
        if(color == WHITE && piece == ROOK && move.departure == 8) return false;
        return move.destination != 8;
    }

    private static boolean updateBlackKingsideCastleRights(Move move, byte color, byte piece, State previousState) {
        if(!previousState.canBlackCastleKingside) return false;
        if(color == BLACK && piece == KING) return false;
        if(color == BLACK && piece == ROOK && move.departure == 57) return false;
        return move.destination != 57;
    }

    private static boolean updateBlackQueensideCastleRights(Move move, byte color, byte piece, State updatedState) {
        if(!updatedState.canBlackCastleQueenside) return false;
        if(color == BLACK && piece == KING) return false;
        if(color == BLACK && piece == ROOK && move.departure == 64) return false;
        return move.destination != 64;
    }

    private void updateBoardRepresentationsAfterMove(Move move, byte color, byte piece) {
        byte pieceOnDestination = board.getPieceOnSquare(move.destination);
        byte typeOfPieceOnDestination = BoardHelper.getPieceType(pieceOnDestination);

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

    private void makeQuietMove(Move move, byte color, byte piece) {
        board.deletePieceFromSquare(move.departure, color, piece);
        board.addPieceOnSquare(move.destination, color, piece);
    }

    private void makeCaptureMove(Move move, byte color, byte piece, byte typeOfPieceOnDestination) {
        byte opponentColor = (color == WHITE) ? BLACK : WHITE;

        board.deletePieceFromSquare(move.destination, opponentColor, typeOfPieceOnDestination);
        makeQuietMove(move, color, piece);
    }

    private void makeEpCaptureMove(Move move, byte color, byte piece) {
        byte opponentColor = (color == WHITE) ? BLACK : WHITE;

        byte deletedPawnIndex = (color == WHITE) ? (byte) (move.destination - 8) : (byte) (move.destination + 8);
        board.deletePieceFromSquare(deletedPawnIndex, opponentColor, PAWN);

        makeQuietMove(move, color, piece);
    }

    private void makeKingCastleMove(byte color) {
        if(color == WHITE) makeWhiteKingCastleMove();
        else makeBlackKingCastleMove();
    }

    private void makeWhiteKingCastleMove() {
        board.deletePieceFromSquare((byte) 4, WHITE, KING);
        board.deletePieceFromSquare((byte) 1, WHITE, ROOK);
        board.addPieceOnSquare((byte) 2, WHITE, KING);
        board.addPieceOnSquare((byte) 3, WHITE, ROOK);
    }

    private void makeBlackKingCastleMove(){
        board.deletePieceFromSquare((byte) 60, BLACK, KING);
        board.deletePieceFromSquare((byte) 57, BLACK, ROOK);
        board.addPieceOnSquare((byte) 58, BLACK, KING);
        board.addPieceOnSquare((byte) 59, BLACK, ROOK);
    }

    private void makeQueenCastleMove(byte color){
        if(color == WHITE) makeWhiteQueenCastleMove();
        else makeBlackQueenCastleMove();
    }

    private void makeWhiteQueenCastleMove(){
        board.deletePieceFromSquare((byte) 4, WHITE, KING);
        board.deletePieceFromSquare((byte) 8, WHITE, ROOK);
        board.addPieceOnSquare((byte) 6, WHITE, KING);
        board.addPieceOnSquare((byte) 5, WHITE, ROOK);
    }

    private void makeBlackQueenCastleMove() {
        board.deletePieceFromSquare((byte) 60, BLACK, KING);
        board.deletePieceFromSquare((byte) 64, BLACK, ROOK);
        board.addPieceOnSquare((byte) 62, BLACK, KING);
        board.addPieceOnSquare((byte) 61, BLACK, ROOK);
    }

    private void makePromotionMove(Move move, byte color, byte pieceToPut) {
        board.deletePieceFromSquare(move.departure, color, PAWN);
        board.addPieceOnSquare(move.destination, color, pieceToPut);
    }

    private void makePromotionWithCaptureMove(Move move, byte color, byte pieceToPut, byte typeOfPieceOnDestination) {
        byte opponentColor = (color == WHITE) ? BLACK : WHITE;
        board.deletePieceFromSquare(move.destination, opponentColor, typeOfPieceOnDestination);
        makePromotionMove(move, color, pieceToPut);
    }
}
