package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveConstants;

import java.util.ArrayList;

public class PawnMoveGenerator extends Generator {

    long sameColorPawns;

    public PawnMoveGenerator(Board board) {
        super(board);
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(64);

        sameColorPawns = board.getSpecificPiecesBitBoard((short) (myColor|PAWN));
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        moveForwardByOne(myColor);
        moveForwardByTwo(myColor);

        moveCaptureLeftSide(myColor);
        moveCaptureRightSide(myColor);

        enPassantLeft(myColor);
        enPassantRight(myColor);

        return possibleMoves;
    }

    private void moveForwardByOne(short myColor) {
        long mask = allEmpty;

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 8);
            addMovesForSide(mask, rank8, (byte) -8, false);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 8);
            addMovesForSide(mask, rank1, (byte) 8,false);
        }
    }

    private void moveForwardByTwo(short myColor) {

        long mask = allEmpty;

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 16);
            mask &= (Long.rotateLeft(allEmpty, 8));
            mask &= rank4;
            addMovesFromMask(mask, DOUBLE_PAWN_PUSH, (byte) -16);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 16);
            mask &= (Long.rotateRight(allEmpty, 8));
            mask &= rank5;
            addMovesFromMask(mask, DOUBLE_PAWN_PUSH, (byte) 16);
        }
    }

    private void moveCaptureLeftSide(short myColor) {

        long mask = allOpponentColor;

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 9);
            mask &=~ rank8;
            mask &=~ fileH;

            addMovesForSide(mask, rank8, (byte) -9, true);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 9);
            mask &=~ rank1;
            mask &=~ fileA;

            addMovesForSide(mask, rank1, (byte) 9, true);
        }
    }

    private void moveCaptureRightSide(short myColor) {

        long mask = allOpponentColor;

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 7);
            mask &=~ rank8;
            mask &=~ fileA;

            addMovesForSide(mask, rank8, (byte) -7, true);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 7);
            mask &=~ rank1;
            mask &=~ fileH;

            addMovesForSide(mask, rank1, (byte) 7, true);
        }

    }

    private void enPassantLeft(short myColor) {
        short target = board.getEnPassantTarget();
        if (target == 0) return;

        long mask = (1L << (target-1));

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 9);
            mask &=~ fileH;

            if(mask != 0L)
                addMovesFromMask(mask, EP_CAPTURE, (byte) -9);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 9);
            mask &=~ fileA;

            if(mask != 0L)
                addMovesFromMask(mask, EP_CAPTURE, (byte) 9);
        }
    }

    private void enPassantRight(short myColor) {
        short target = board.getEnPassantTarget();
        if (target == 0) return;

        long mask = (1L << (target-1));

        if(myColor == WHITE) {
            mask &= Long.rotateLeft(sameColorPawns, 7);
            mask &=~ fileA;

            if(mask != 0L)
                addMovesFromMask(mask, EP_CAPTURE, (byte) -7);
        }
        else {
            mask &= Long.rotateRight(sameColorPawns, 7);
            mask &=~ fileH;

            if(mask != 0L)
                addMovesFromMask(mask, EP_CAPTURE, (byte) 7);
        }
    }

    private void addMovesForSide(long mask, long finalRank, byte shift, boolean withCapture) {
        byte moveType = withCapture ? CAPTURES : QUIET_MOVE;
        int promotionType = withCapture ? 4 : 0;
        addMovesFromMask(mask &~ finalRank, moveType, shift);

        if((mask & finalRank) != 0) {
            addMovesFromMask(mask & finalRank, (byte) (KNIGHT_PROMOTION + promotionType), shift);
            addMovesFromMask(mask & finalRank, (byte) (BISHOP_PROMOTION + promotionType), shift);
            addMovesFromMask(mask & finalRank, (byte) (ROOK_PROMOTION + promotionType), shift);
            addMovesFromMask(mask & finalRank, (byte) (QUEEN_PROMOTION + promotionType), shift);
        }
    }
}
