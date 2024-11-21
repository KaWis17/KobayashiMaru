package org.example.Engine.StateEvaluation.Evaluators;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.StateEvaluation.Evaluation;

import static org.example.Engine.MoveGeneration.BitBoardHelper.fileA;
import static org.example.Engine.MoveGeneration.BitBoardHelper.fileH;

public class PawnsStructure implements Evaluation {

    Board board;
    int weight;
    long[][] arrayOfPassedPawns;
    long[][] arrayOfIsolatedPawns;
    long[] arrayOfDoubledPawns;

    public PawnsStructure(Board board, int weight){
        this.board = board;
        this.weight = weight;

        arrayOfPassedPawns = generatePassedMask();
        arrayOfIsolatedPawns = generateIsolatedMask();
        arrayOfDoubledPawns = generateDoubledMask();
    }

    private long[][] generatePassedMask(){
        long[][] passedPawns = new long[2][64];
        for (int i = 0; i < 64; i++) {
            int column = i % 8;
            int row = i / 8;
            long leftColumnMask = (column == 0) ? 0 : 0x0101010101010101L << (column - 1);
            long centerColumnMask = 0x0101010101010101L << column;
            long rightColumnMask = (column == 7) ? 0 : 0x0101010101010101L << (column + 1);

            for (int j = row+1; j < 8; j++) {
                passedPawns[0][i] |= centerColumnMask << (j * 8);
                if (column != 0) {
                    passedPawns[0][i] |= leftColumnMask << (j * 8);
                }
                if (column != 7) {
                    passedPawns[0][i] |= rightColumnMask << (j * 8);
                }
            }

            for (int j = row-1; j >= 0; j--) {
                passedPawns[1][i] |= centerColumnMask >>> ((7 - j) * 8);
                if (column != 0) {
                    passedPawns[1][i] |= leftColumnMask >>> ((7 - j) * 8);
                }
                if (column != 7) {
                    passedPawns[1][i] |= rightColumnMask >>> ((7 - j) * 8);
                }
            }
        }
        return passedPawns;
    }

    public long[][] generateIsolatedMask(){
        long[][] isolatedPawns = new long[64][2];
        for (int i = 0; i < 64; i++) {
            int column = i % 8;
            long leftColumnMask = (column == 0) ? 0 : 0x0101010101010101L << (column - 1);
            long rightColumnMask = (column == 7) ? 0 : 0x0101010101010101L << (column + 1);

            isolatedPawns[i][0] = leftColumnMask;
            isolatedPawns[i][1] = rightColumnMask;
        }
        return isolatedPawns;
    }

    private long[] generateDoubledMask(){
        long[] doubledPawns = new long[64];
        for (int i = 0; i < 64; i++) {
            doubledPawns[i] = 0x0101010101010101L << (i % 8);
        }
        return doubledPawns;
    }

    @Override
    public int evaluate() {
        if(!Config.PAWN_STRUCTURE_ON)
            return 0;
        return weight * (evaluateColor(board.WHITE) - evaluateColor(board.BLACK));
    }

    private int evaluateColor(byte color) {
        byte opponentColor = color == Board.WHITE ? Board.BLACK : Board.WHITE;

        long pawns = board.getSpecificBitBoard((byte) (color | Board.PAWN));
        long pawnsToIterate = pawns;
        long opponentPawns = board.getSpecificBitBoard((byte) (opponentColor | Board.PAWN));

        int evaluation = 3 * evaluateSupport(pawns, color);
        while(pawnsToIterate != 0){
            long pawn = pawnsToIterate & -pawnsToIterate;
            pawnsToIterate &= pawnsToIterate - 1;

            int pawnIndex = Long.numberOfTrailingZeros(pawn);
            long otherPawns = pawns & ~pawn;

            evaluation += 2 * evaluateColumns(pawnIndex, pawn, otherPawns);
            evaluation += evaluateDoubledPawns(pawnIndex, otherPawns);
            evaluation += evaluatePassedPawns(pawnIndex, color, opponentPawns);
        }

        return weight * evaluation;
    }

    private int evaluateSupport(long pawns, byte color) {
        long rightPawns;
        long leftPawns;

        if(color == Board.WHITE){
            rightPawns = Long.rotateRight(pawns, 9);
            rightPawns &=~ fileA;
            leftPawns = Long.rotateRight(pawns, 7);
            leftPawns &=~ fileH;
        }
        else{
            rightPawns = Long.rotateLeft(pawns, 9);
            rightPawns &=~ fileH;
            leftPawns = Long.rotateLeft(pawns, 7);
            leftPawns &=~ fileA;
        }
        long leftProtection = leftPawns & pawns;
        long rightProtection = rightPawns & pawns;

        return Long.bitCount(leftProtection) + Long.bitCount(rightProtection);
    }

    private int evaluateColumns(int pawnIndex, long pawn, long otherPawns) {
        int evaluation = 0;
        if ((otherPawns & arrayOfIsolatedPawns[pawnIndex][0]) == 0 && (pawn & fileH) == 0)
            evaluation -= 1;
        if ((otherPawns & arrayOfIsolatedPawns[pawnIndex][1]) == 0 && (pawn & fileA) == 0)
            evaluation -= 1;

        return evaluation;
    }

    private int evaluateDoubledPawns(int pawnIndex, long otherPawns) {
        int inRow = Long.bitCount(arrayOfDoubledPawns[pawnIndex] & otherPawns);
        if (inRow >= 2) return -3;
        if (inRow == 1 ) return -2;
        return 0;
    }

    private int evaluatePassedPawns(int pawnIndex, byte color, long opponentPawns) {
        int row = pawnIndex / 8;
        int side = color == Board.WHITE ? 0 : 1;
        if ((opponentPawns & arrayOfPassedPawns[side][pawnIndex]) == 0) {
            if(color == Board.WHITE)
                return row;
            else
                return 7 - row;
        }
        return 0;
    }
}
