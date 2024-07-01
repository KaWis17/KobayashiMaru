package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class KnightMoveGenerator extends Generator {

    long sameColorKnights;

    public long[] preComputedMasks = new long[64];

    public KnightMoveGenerator(Board board) {
        super(board);

        initiatePrecomputedMasks();
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(64);

        sameColorKnights = board.getSpecificPiecesBitBoard((short) (myColor | KNIGHT));
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        jump(sameColorKnights);

        return possibleMoves;
    }

    // generate 64 masks for every field
    private void jump(long knights) {

        long next = knights & -knights;

        while (next != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(next));

            long maskToAdd = preComputedMasks[index-1];
            long quietMask = maskToAdd & allEmpty;

            long captureMask = maskToAdd & allOpponentColor;

            addMovesFromMaskWithStartIndex(quietMask, QUIET_MOVE, index);
            addMovesFromMaskWithStartIndex(captureMask, CAPTURES, index);

            knights &= ~(next);
            next = knights & -knights;
        }
    }

    private void initiatePrecomputedMasks() {
        for(int i=0; i<64; i++) {
            long knight = Long.rotateLeft(1L, i);

            boolean notInRank1 = ((knight & rank1) == 0);
            boolean notInRank2 = ((knight & rank2) == 0);
            boolean notInRank7 = ((knight & rank7) == 0);
            boolean notInRank8 = ((knight & rank8) == 0);

            boolean notInFileA = ((knight & fileA) == 0);
            boolean notInFileB = ((knight & fileB) == 0);
            boolean notInFileG = ((knight & fileG) == 0);
            boolean notInFileH = ((knight & fileH) == 0);

            long mask = 0;

            if (notInFileH && notInRank7 && notInRank8) mask = mask | Long.rotateLeft(knight, 15); // TR
            if (notInFileG && notInFileH && notInRank8) mask = mask | Long.rotateLeft(knight,6); // RT
            if (notInFileG && notInFileH && notInRank1) mask = mask | Long.rotateRight(knight, 10); // RB
            if (notInFileH && notInRank1 && notInRank2) mask = mask | Long.rotateRight(knight, 17); // BR

            if (notInFileA && notInRank1 && notInRank2) mask = mask | Long.rotateRight(knight, 15); // BL
            if (notInFileA && notInFileB && notInRank1) mask = mask | Long.rotateRight(knight, 6); // LB
            if (notInFileA && notInFileB && notInRank8) mask = mask | Long.rotateLeft(knight, 10); // LT
            if (notInFileA && notInRank7 && notInRank8) mask = mask | Long.rotateLeft(knight, 17); // TL

            preComputedMasks[i] = mask;
        }
    }

    public long getKingAsFigureDangerMask(short myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty) {
        short opponentColor = myColor == WHITE ? BLACK : WHITE;
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        int index = 64 - Long.numberOfLeadingZeros(myKing);
        long kingAsKnightMoves = preComputedMasks[index-1];

        return (kingAsKnightMoves & board.getSpecificPiecesBitBoard((short) (opponentColor | KNIGHT)));
    }

}
