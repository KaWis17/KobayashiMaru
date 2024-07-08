package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;

public class Move implements MoveConstants, BoardHelper {

    public byte departure;
    public byte destination;
    public byte type;

    public Move(byte from, byte to, byte type) {
        this.departure = from;
        this.destination = to;
        this.type = type;
    }

    public Move(String move, Board board) {
        this.departure = (byte) BoardHelper.squareStringToNumber(move.substring(0, 2));
        this.destination = (byte) BoardHelper.squareStringToNumber(move.substring(2, 4));

        short pieceToBeMoved = board.getPieceOnSquare(departure);
        short pieceOnDestination = board.getPieceOnSquare(destination);

        this.type = decideType(move, pieceToBeMoved, pieceOnDestination, board.getEnPassantTarget());
    }

    private byte decideType(String move, short pieceToBeMoved, short pieceOnDestination, short enPassantTarget) {
        if(move.length() == 4) {
            return decideNotPromotionType(pieceToBeMoved, pieceOnDestination, enPassantTarget);
        }
        else {
            return decidePromotionType(move, pieceOnDestination);
        }
    }

    private byte decideNotPromotionType(short pieceToBeMoved, short pieceOnDestination, short enPassantTarget) {
        if(isDoublePawnPush(pieceToBeMoved, departure, destination))
            return DOUBLE_PAWN_PUSH;

        if(isCastle(pieceToBeMoved, departure, destination)) {
            if(isQueenCastle(departure, destination))
                return QUEEN_CASTLE;

            return KING_CASTLE;
        }

        if(isCapture(pieceOnDestination)) {
            if(isEnPassantCapture(pieceToBeMoved, enPassantTarget, destination))
                return EP_CAPTURE;

            return CAPTURES;
        }

        return QUIET_MOVE;
    }

    private byte decidePromotionType(String move, short pieceOnDestination) {
        switch(move.charAt(4)) {
            case 'r' -> type = ROOK_PROMOTION;
            case 'b' -> type = BISHOP_PROMOTION;
            case 'n' -> type = KNIGHT_PROMOTION;
            default -> type = QUEEN_PROMOTION;
        }

        return (pieceOnDestination == 0) ? type : (byte) (type + 4);
    }

    private boolean isDoublePawnPush(short pieceToBeMoved, byte departure, byte destination) {
        return  (pieceToBeMoved == (WHITE|PAWN) || pieceToBeMoved == (BLACK|PAWN))
                && Math.abs(departure - destination) == 16;
    }

    private boolean isCastle(short pieceToBeMoved, byte departure, byte destination) {
        return  (pieceToBeMoved == (WHITE|KING) || pieceToBeMoved == (BLACK|KING))
                && Math.abs(departure - destination) == 2;
    }

    private boolean isQueenCastle(byte departure, byte destination) {
        return  destination > departure;
    }

    private boolean isCapture(short pieceOnDestination) {
        return pieceOnDestination != 0;
    }

    private boolean isEnPassantCapture(short pieceToBeMoved, short enPassantTarget, byte destination) {
        return  (pieceToBeMoved == (WHITE|PAWN) || pieceToBeMoved == (BLACK|PAWN))
                && destination == enPassantTarget;
    }

    @Override
    public String toString() {
        String move = BoardHelper.squareNumberToString(departure);
        move += BoardHelper.squareNumberToString(destination);

        if(type == QUEEN_PROMOTION || type == QUEEN_PROMOTION_CAPTURE)
            move += "q";
        else if(type == ROOK_PROMOTION || type == ROOK_PROMOTION_CAPTURE)
            move += "r";
        else if(type == BISHOP_PROMOTION || type == BISHOP_PROMOTION_CAPTURE)
            move += "b";
        else if(type == KNIGHT_PROMOTION || type == KNIGHT_PROMOTION_CAPTURE)
            move += "n";

        return move;
    }

}
