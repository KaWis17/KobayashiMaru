package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class PawnMoveGenerator extends Generator {

    long sameColorPawns;

    public PawnMoveGenerator(Board board) {
        super(board);
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(64);

        sameColorPawns = board.bitBoardsRepresentation.bitBoards[myColor|PAWN];
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        moveForwardByOne();
        moveForwardByTwo();

        return possibleMoves;
    }

    private void moveForwardByOne() {

        long mask;

        if(board.currentBoardState.whiteToMove) {
            mask = sameColorPawns << 8;
            mask = mask & allEmpty;
            mask = mask & ~rank8;
            addMovesFromMask(mask, QUIET_MOVE, (byte) -8);
        }
        else {
            mask = sameColorPawns >> 8;
            mask = mask & allEmpty;
            mask = mask & ~rank1;
            addMovesFromMask(mask, QUIET_MOVE, (byte) 8);
        }
    }

    private void moveForwardByTwo() {

        long mask;

        if(board.currentBoardState.whiteToMove) {
            mask = sameColorPawns << 16; // move mask by two rows up
            mask = mask & allEmpty; // check if there is no pieces
            mask = mask & (allEmpty << 8); // check if row between is empty
            mask = mask & rank4; // check if its on the 4th line
            addMovesFromMask(mask, DOUBLE_PAWN_PUSH, (byte) -16);
        }
        else {
            mask = sameColorPawns >> 16; // move mask by two rows up
            mask = mask & allEmpty; // check if there is no pieces
            mask = mask & (allEmpty >> 8); // check if row between is empty
            mask = mask & rank5; // check if its on the 4th line
            addMovesFromMask(mask, DOUBLE_PAWN_PUSH, (byte) 16);
        }
    }

    private void addMovesFromMask(long mask, byte type, byte movedBy) {
        while(mask != 0) {
            byte index = (byte) (64 - Long.numberOfLeadingZeros(mask));
            possibleMoves.add(new Move((byte) (index + movedBy), index, type));
            mask &= ~(1L << (index - 1));
        }
    }

    private void printMask(long mask) {
        System.out.println(String.format("%64s", Long.toBinaryString(mask)).replace(' ', '0'));
    }
}
