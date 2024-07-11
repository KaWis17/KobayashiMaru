package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class SlidingMoveGenerator extends Generator {

    public SlidingMoveGenerator(Board board) {
        super(board);
    }

    @Override
    public ArrayList<Move> generateMoves(byte myColor, long allMyColor, long allOpponentColor, long allEmpty) {

        possibleMoves = new ArrayList<>(64);

        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        long rooks = board.getSpecificBitBoard((byte) (myColor | ROOK));
        generateMovesForVerticalAndHorizontal(rooks);

        long bishops = board.getSpecificBitBoard((byte) (myColor | BISHOP));
        generateMovesForDiagonals(bishops);

        long queens = board.getSpecificBitBoard((byte) (myColor | QUEEN));
        generateMovesForVerticalAndHorizontal(queens);
        generateMovesForDiagonals(queens);

        return possibleMoves;
    }

    @Override
    public long getKingAsFigureDangerMask(byte myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty) {

        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        byte opponentColor = myColor == WHITE ? BLACK : WHITE;

        long verticalAndHorizontalDangerMask = generateMovesForVerticalAndHorizontal(myColor, myKing);
        long verticalAndHorizontalDangerQueenMask = verticalAndHorizontalDangerMask & board.getSpecificBitBoard((byte) (opponentColor | QUEEN));
        long verticalAndHorizontalDangerRookMask = verticalAndHorizontalDangerMask & board.getSpecificBitBoard((byte) (opponentColor | ROOK));

        verticalAndHorizontalDangerMask = verticalAndHorizontalDangerQueenMask | verticalAndHorizontalDangerRookMask;

        long diagonalDangerMask = getMoveMaskForKing(myKing);
        long diagonalDangerQueenMask = diagonalDangerMask & board.getSpecificBitBoard((byte) (opponentColor | QUEEN));
        long diagonalDangerBishopMask = diagonalDangerMask & board.getSpecificBitBoard((byte) (opponentColor | BISHOP));

        diagonalDangerMask = diagonalDangerQueenMask | diagonalDangerBishopMask;

        return verticalAndHorizontalDangerMask | diagonalDangerMask;
    }

    private void generateMovesForVerticalAndHorizontal(long figures) {

        long next = figures & -figures;
        while (next != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(next));
            byte arrayIndex = (byte) (index-1);

            long horizontalMask = applyHyperbolaQuintessence(next, ranks[(arrayIndex)/8], allMyColor);
            long verticalMask = applyHyperbolaQuintessence(next, files[(arrayIndex)%8], allMyColor);
            long mask = verticalMask | horizontalMask;

            addMovesFromMaskWithStartIndex(mask & allOpponentColor, CAPTURES, index);
            addMovesFromMaskWithStartIndex(mask & allEmpty, QUIET_MOVE, index);

            figures &=~ (next);
            next = figures & -figures;
        }
    }

    private long generateMovesForVerticalAndHorizontal(byte myColor, long myKing) {
        byte index = (byte) (64 - Long.numberOfLeadingZeros(myKing));
        byte arrayIndex = (byte) (index-1);

        long horizontalMask = applyHyperbolaQuintessence(myKing, ranks[(arrayIndex)/8], allMyColor);
        long verticalMask = applyHyperbolaQuintessence(myKing, files[(arrayIndex)%8], allMyColor);
        return verticalMask | horizontalMask;
    }

    public void generateMovesForDiagonals(long figures) {
        long next = figures & -figures;
        while (next != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(next));
            byte arrayIndex = (byte) (index-1);

            long majorsMask = applyHyperbolaQuintessence(next, majorDiagonals[(arrayIndex%8) + 7 - (arrayIndex/8)], allMyColor);
            long minorsMask = applyHyperbolaQuintessence(next, minorDiagonals[(arrayIndex%8) + (arrayIndex/8)], allMyColor);

            long mask = majorsMask | minorsMask;

            addMovesFromMaskWithStartIndex(mask & allOpponentColor, CAPTURES, index);
            addMovesFromMaskWithStartIndex(mask & allEmpty, QUIET_MOVE, index);

            figures &=~ (next);
            next = figures & -figures;
        }
    }

    private long getMoveMaskForKing(long myKing) {
        byte index = (byte) (64 - Long.numberOfLeadingZeros(myKing));
        byte arrayIndex = (byte) (index-1);

        long majorsMask = applyHyperbolaQuintessence(myKing, majorDiagonals[(arrayIndex%8) + 7 - (arrayIndex/8)], allMyColor);
        long minorsMask = applyHyperbolaQuintessence(myKing, minorDiagonals[(arrayIndex%8) + (arrayIndex/8)], allMyColor);
        return majorsMask | minorsMask;
    }

    private long applyHyperbolaQuintessence(long piece, long mask, long maskForMyColor) {
        long occupiedOnMask = (mask & ~allEmpty);

        long mask1 = occupiedOnMask - (2 * piece);
        long mask2 = Long.reverse(Long.reverse(occupiedOnMask) - (2 * Long.reverse(piece)));

        return ((mask1 ^ mask2) & mask) & ~maskForMyColor;
    }
}
