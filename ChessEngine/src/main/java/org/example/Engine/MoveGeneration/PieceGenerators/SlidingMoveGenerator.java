package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class SlidingMoveGenerator extends Generator {

    public SlidingMoveGenerator(Board board) {
        super(board);
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {

        possibleMoves = new ArrayList<>(64);

        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        long rooks = board.getSpecificPiecesBitBoard((short) (myColor | ROOK));
        generateMovesForVerticalAndHorizontal(rooks);

        long bishops = board.getSpecificPiecesBitBoard((short) (myColor | BISHOP));
        generateMovesForDiagonals(bishops);

        //board.bitBoardsRepresentation.bitBoards
        long queens = board.getSpecificPiecesBitBoard((short) (myColor | QUEEN));
        generateMovesForVerticalAndHorizontal(queens);
        generateMovesForDiagonals(queens);

        return possibleMoves;
    }

    private void generateMovesForVerticalAndHorizontal(long figures) {

        long next = figures & -figures;
        while (next != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(next));
            byte arrayIndex = (byte) (index-1);

            long horizontalMask = applyHyperbolaQuintessence(next, ranks[(arrayIndex)/8]);
            long verticalMask = applyHyperbolaQuintessence(next, files[(arrayIndex)%8]);
            long mask = verticalMask | horizontalMask;

            addMovesFromMaskWithStartIndex(mask & allOpponentColor, CAPTURES, index);
            addMovesFromMaskWithStartIndex(mask & allEmpty, QUIET_MOVE, index);

            figures &=~ (next);
            next = figures & -figures;
        }
    }

    public void generateMovesForDiagonals(long figures) {
        long next = figures & -figures;
        while (next != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(next));
            byte arrayIndex = (byte) (index-1);

            long majorsMask = applyHyperbolaQuintessence(next, majorDiagonals[(arrayIndex%8) + 7 - (arrayIndex/8)]);
            long minorsMask = applyHyperbolaQuintessence(next, minorDiagonals[(arrayIndex%8) + (arrayIndex/8)]);
            long mask = majorsMask | minorsMask;

            addMovesFromMaskWithStartIndex(mask & allOpponentColor, CAPTURES, index);
            addMovesFromMaskWithStartIndex(mask & allEmpty, QUIET_MOVE, index);

            figures &=~ (next);
            next = figures & -figures;
        }
    }

    private long applyHyperbolaQuintessence(long piece, long mask) {
        long occupiedOnMask = (mask & ~allEmpty);
        long mask1 = occupiedOnMask - (2 * piece);
        long mask2 = Long.reverse(Long.reverse(occupiedOnMask) - (2 * Long.reverse(piece)));

        return ((mask1 ^ mask2) & mask) & ~allMyColor;
    }
}
