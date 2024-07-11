package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveConstants;
import org.example.Engine.MoveGeneration.BitBoardHelper;

import java.util.ArrayList;

public abstract class Generator implements BoardHelper, BitBoardHelper, MoveConstants {

    Board board;
    long allOpponentColor;
    long allMyColor;
    long allEmpty;

    ArrayList<Move> possibleMoves = new ArrayList<>(64);
    public abstract ArrayList<Move> generateMoves(byte myColor, long allMyColor, long allOpponentColor, long allEmpty);
    public abstract long getKingAsFigureDangerMask(byte myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty);
    Generator(Board board) {
        this.board = board;
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
