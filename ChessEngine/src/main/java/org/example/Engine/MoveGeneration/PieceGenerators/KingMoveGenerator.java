package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.AttackOfKingGenerator;

import java.util.ArrayList;

public class KingMoveGenerator extends Generator {

    long[] preComputedMasks = new long[64];
    AttackOfKingGenerator checkGenerator;

    public KingMoveGenerator(Board board, AttackOfKingGenerator checkGenerator) {
        super(board);
        this.checkGenerator = checkGenerator;
        initiatePrecomputedMasks();
    }

    @Override
    public ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(12);

        addAllPseudoLegalMoves(myColor, allOpponentColor, allEmpty);
        addCastlingMoves(myColor);
        deletePseudoLegalCastling(myColor);
        deletePseudoLegalMovesThatPutKingInCheck(myColor);

        return possibleMoves;
    }

    @Override
    public long getKingAsFigureDangerMask(short myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty) {
        return 0;
    }

    private void addAllPseudoLegalMoves(short myColor, long allOpponentColor, long allEmpty) {
        long king = board.getSpecificPiecesBitBoard((short) (myColor|KING));

        byte index = (byte) (64 - Long.numberOfLeadingZeros(king));

        long maskToAdd = preComputedMasks[index-1];

        long quietMask = maskToAdd & allEmpty;
        long captureMask = maskToAdd & allOpponentColor;

        addMovesFromMaskWithStartIndex(quietMask, QUIET_MOVE, index);
        addMovesFromMaskWithStartIndex(captureMask, CAPTURES, index);
    }

    private void deletePseudoLegalMovesThatPutKingInCheck(short myColor) {
        ArrayList<Move> legalMoves = new ArrayList<>(12);

        for(Move move : possibleMoves) {
            board.makeMove(move);
            if(!checkGenerator.isKingInCheck(myColor))
                legalMoves.add(move);

            board.unmakeMove();
        }

        possibleMoves = legalMoves;
    }

    private void addCastlingMoves(short myColor) {
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

    private void deletePseudoLegalCastling(short myColor) {
        ArrayList<Move> legalMoves = new ArrayList<>(12);
        for(Move move: possibleMoves) {
            if(move.type != KING_CASTLE && move.type != QUEEN_CASTLE) {
                legalMoves.add(move);
            }
            else {
                if(myColor == WHITE) {
                    if(move.type == KING_CASTLE) {
                        int checks = 0;
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;

                        board.makeMove(new Move((byte) 4, (byte) 3, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor)) {
                            checks++;
                        }
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);
                    }
                    else {
                        int checks = 0;
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;

                        board.makeMove(new Move((byte) 4, (byte) 5, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;
                        board.unmakeMove();
                        board.makeMove(new Move((byte) 4, (byte) 6, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);

                    }
                }
                else {
                    if(move.type == KING_CASTLE) {
                        int checks = 0;
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;

                        board.makeMove(new Move((byte) 60, (byte) 59, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor)) {
                            checks++;
                        }
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);
                    }
                    else {
                        int checks = 0;
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;

                        board.makeMove(new Move((byte) 60, (byte) 61, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;
                        board.unmakeMove();
                        board.makeMove(new Move((byte) 60, (byte) 62, QUIET_MOVE));
                        if(checkGenerator.isKingInCheck(myColor))
                            checks++;
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);

                    }
                }
            }
        }
        possibleMoves = legalMoves;
    }
}
