package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class KnightMoveGenerator extends Generator {

    long sameColorKnights;

    public KnightMoveGenerator(Board board) {
        super(board);
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(64);

        sameColorKnights = board.bitBoardsRepresentation.bitBoards[myColor|KNIGHT];
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        jump(sameColorKnights);

        return possibleMoves;
    }

    private void jump(long knights) {
        while(knights != 0) {
            byte knightIndex = (byte) (64 - Long.numberOfLeadingZeros(knights));

            System.out.println(String.format("%64s", Long.toBinaryString(knights)).replace(' ', '0'));

            knights &= ~(1L << (knightIndex - 1));
        }
    }

}
