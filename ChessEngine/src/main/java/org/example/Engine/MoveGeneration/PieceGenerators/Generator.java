package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveConstants;
import org.example.Engine.MoveGeneration.BitBoardConstants;

import java.util.ArrayList;

public abstract class Generator implements BoardConstants, BitBoardConstants, MoveConstants {

    Board board;
    long allOpponentColor;
    long allMyColor;
    long allEmpty;

    ArrayList<Move> possibleMoves = new ArrayList<>(64);
    public abstract ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty);
    public abstract long getKingAsFigureDangerMask(short myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty);
    Generator(Board board) {
        this.board = board;
    }

    public static void printMask(long mask) {
        String binaryString = String.format("%64s", Long.toBinaryString(mask)).replace(' ', '0');
        for (int i = 0; i < binaryString.length(); i += 8) {
            System.out.println(binaryString.substring(i, i + 8));
        }
        System.out.println();
    }

    void addMovesFromMask(long mask, byte type, byte movedBy) {
        while(mask != 0L) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(mask));
            possibleMoves.add(new Move((byte) (index + movedBy), index, type));
            mask &= ~(1L << (index - 1));
        }
    }

    void addMovesFromMaskWithStartIndex(long mask, byte type, byte startIndex) {
        while(mask != 0L) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(mask));
            possibleMoves.add(new Move(startIndex, index, type));
            mask &= ~(1L << (index - 1));
        }
    }

}
