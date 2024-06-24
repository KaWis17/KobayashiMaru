package org.example.Engine.BoardRepresentation.Move;

import org.example.Engine.BoardRepresentation.SquareCalculator;

public class Move implements MoveConstants {

    public byte departure;
    public byte destination;
    public byte type;

    public Move(byte from, byte to, byte type) {
        this.departure = from;
        this.destination = to;
        this.type = type;
    }

    public Move(String move) {

        //TODO not finished
        this.departure = SquareCalculator.calculate(move.substring(0, 2));
        this.destination = SquareCalculator.calculate(move.substring(2, 4));

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
        }
    }

    @Override
    public String toString() {
        String move = SquareCalculator.calculate(departure);
        move += SquareCalculator.calculate(destination);

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
