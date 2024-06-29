package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class KingMoveGenerator extends Generator {

    long[] preComputedMasks = new long[64];

    public KingMoveGenerator(Board board) {
        super(board);

        initiatePrecomputedMasks();
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(64);

        addAllPseudoLegalMoves(myColor, allMyColor, allOpponentColor, allEmpty);
//        removesKingMovesThatPutKingInCheck(myColor);
        return possibleMoves;
    }

    private void addAllPseudoLegalMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        long king = board.getSpecificPiecesBitBoard((short) (myColor|KING));

        //TODO: delete later
        if(king == 0L)
            return;

        byte index = (byte) (64 - Long.numberOfLeadingZeros(king));

        long maskToAdd = preComputedMasks[index-1];

        long quietMask = maskToAdd & allEmpty;
        long captureMask = maskToAdd & allOpponentColor;

        addMovesFromMaskWithStartIndex(quietMask, QUIET_MOVE, index);
        addMovesFromMaskWithStartIndex(captureMask, CAPTURES, index);

        addCastlingMoves(myColor, allMyColor, allOpponentColor, allEmpty);
    }

//    private void removesKingMovesThatPutKingInCheck(short myColor) {
//        ArrayList<Move> legalMoves = new ArrayList<>(possibleMoves.size());
//
//
//        possibleMoves = legalMoves;
//    }

    private void addCastlingMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        if(myColor == WHITE) {
            if(board.currentBoardState.canWhiteCastleKingside) {
                if (board.getPieceOnSquare((short) 2) == 0 && board.getPieceOnSquare((short) 3) == 0)
                    possibleMoves.add(new Move((byte) 4, (byte) 2, KING_CASTLE));
            }
            if(board.currentBoardState.canWhiteCastleQueenside) {
                if (board.getPieceOnSquare((short) 5) == 0 && board.getPieceOnSquare((short) 6) == 0 && board.getPieceOnSquare((short) 7) == 0)
                    possibleMoves.add(new Move((byte) 4, (byte) 6, QUEEN_CASTLE));
            }
        }
        else {
            if(board.currentBoardState.canBlackCastleKingside) {
                if (board.getPieceOnSquare((short) 58) == 0 && board.getPieceOnSquare((short) 59) == 0)
                    possibleMoves.add(new Move((byte) 60, (byte) 58, KING_CASTLE));
            }
            if(board.currentBoardState.canBlackCastleQueenside) {
                if (board.getPieceOnSquare((short) 61) == 0 && board.getPieceOnSquare((short) 62) == 0 && board.getPieceOnSquare((short) 63) == 0)
                    possibleMoves.add(new Move((byte) 60, (byte) 62, QUEEN_CASTLE));
            }
        }
    }

    private void initiatePrecomputedMasks() {
        for(int i=0; i<64; i++) {
            long king = Long.rotateLeft(1L, i);

            boolean notInRank1 = ((king & rank1) == 0);
            boolean notInRank8 = ((king & rank8) == 0);

            boolean notInFileA = ((king & fileA) == 0);
            boolean notInFileH = ((king & fileH) == 0);

            long mask = 0;

            if(notInFileA) mask = mask | Long.rotateLeft(king, 1); // L
            if(notInFileH) mask = mask | Long.rotateRight(king, 1); // R
            if(notInRank1) mask = mask | Long.rotateRight(king, 8); // B
            if(notInRank8) mask = mask | Long.rotateLeft(king, 8); // T

            if(notInFileH & notInRank8) mask = mask | Long.rotateLeft(king, 7); // BL
            if(notInFileA & notInRank8) mask = mask | Long.rotateLeft(king, 9); // BR
            if(notInFileH & notInRank1) mask = mask | Long.rotateRight(king, 9); // TL
            if(notInFileA & notInRank1) mask = mask | Long.rotateRight(king, 7);

            preComputedMasks[i] = mask;
        }
    }
}
