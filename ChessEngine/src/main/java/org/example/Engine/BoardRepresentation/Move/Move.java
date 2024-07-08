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

        this.type = QUIET_MOVE;

        //check if double pawn push
        if(pieceToBeMoved == (WHITE|PAWN) || pieceToBeMoved == (BLACK|PAWN)) {
            if(Math.abs(departure - destination) == 16)
                type = DOUBLE_PAWN_PUSH;
        }

        //check if castle
        if(pieceToBeMoved == (WHITE|KING) || pieceToBeMoved == (BLACK|KING)) {
            if(Math.abs(departure - destination) == 2) {
                if(destination > departure)
                    type = QUEEN_CASTLE;
                else
                    type = KING_CASTLE;
            }
        }

        //check if capture
        if(pieceOnDestination != 0) {
            type = CAPTURES;
        }

        //check en passant
        if(pieceToBeMoved == (WHITE|PAWN) || pieceToBeMoved == (BLACK|PAWN)) {
            if (destination == board.getEnPassantTarget())
                type = EP_CAPTURE;
        }

        //check if promotion
        if(move.length() == 5) {
            switch(move.charAt(4)) {
                case 'q':
                    type = QUEEN_PROMOTION;
                    break;
                case 'r':
                    type = ROOK_PROMOTION;
                    break;
                case 'b':
                    type = BISHOP_PROMOTION;
                    break;
                case 'n':
                    type = KNIGHT_PROMOTION;
                    break;
            }

            //check for promotion with capture
            if(pieceOnDestination != 0)
                type += 4;
        }
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
