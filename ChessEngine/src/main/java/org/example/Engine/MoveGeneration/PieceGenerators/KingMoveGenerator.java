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
    public ArrayList<Move> generateMoves(byte myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(12);

        addNotCastlingMoves(myColor, allOpponentColor, allEmpty);
        addCastlingMoves(myColor);

        return possibleMoves;
    }

    @Override
    public ArrayList<Move> generateCaptureMoves(byte myColor, long allMyColor, long allOpponentColor, long allEmpty) {
        possibleMoves = new ArrayList<>(12);

        long king = board.getSpecificBitBoard((byte) (myColor|KING));
        byte index = (byte) (64 - Long.numberOfLeadingZeros(king));

        long maskToAdd = preComputedMasks[index-1] & allOpponentColor;

        addMovesFromMaskWithStartIndex(maskToAdd, CAPTURES, index);

        return possibleMoves;
    }

    @Override
    public long getKingAsFigureDangerMask(byte myColor, long myKing, long allMyColor, long allOpponentColor, long allEmpty) {
        byte opponentColor = myColor == WHITE ? BLACK : WHITE;
        this.allMyColor = allMyColor;
        this.allOpponentColor = allOpponentColor;
        this.allEmpty = allEmpty;

        int index = 64 - Long.numberOfLeadingZeros(myKing);
        long kingAsKingMoves = preComputedMasks[index-1];

        return (kingAsKingMoves & board.getSpecificBitBoard((byte) (opponentColor | KING)));
    }

    private void addNotCastlingMoves(byte myColor, long allOpponentColor, long allEmpty) {
        long king = board.getSpecificBitBoard((byte) (myColor|KING));

        byte index = (byte) (64 - Long.numberOfLeadingZeros(king));

        long maskToAdd = preComputedMasks[index-1];

        long quietMask = maskToAdd & allEmpty;
        long captureMask = maskToAdd & allOpponentColor;

        addMovesFromMaskWithStartIndex(quietMask, QUIET_MOVE, index);
        addMovesFromMaskWithStartIndex(captureMask, CAPTURES, index);
    }

    private void addCastlingMoves(byte myColor) {
        ArrayList<Move> castlingMoves;

        if(myColor == WHITE)
            castlingMoves = getCastlingMovesForWhite();
        else
            castlingMoves = getCastlingMovesForBlack();

        castlingMoves = deletePseudoLegalCastling(myColor, castlingMoves);

        possibleMoves.addAll(castlingMoves);
    }

    private ArrayList<Move> getCastlingMovesForWhite() {
        ArrayList<Move> castlingMoves = new ArrayList<>(2);

        if(board.currentBoardState.canWhiteCastleKingside) {
            if (board.getPieceOnSquare((byte) 2) == 0 && board.getPieceOnSquare((byte) 3) == 0)
                castlingMoves.add(new Move((byte) 4, (byte) 2, KING_CASTLE));
        }
        if(board.currentBoardState.canWhiteCastleQueenside) {
            if (board.getPieceOnSquare((byte) 5) == 0 && board.getPieceOnSquare((byte) 6) == 0 && board.getPieceOnSquare((byte) 7) == 0)
                castlingMoves.add(new Move((byte) 4, (byte) 6, QUEEN_CASTLE));
        }

        return castlingMoves;
    }

    private ArrayList<Move> getCastlingMovesForBlack() {
        ArrayList<Move> castlingMoves = new ArrayList<>(2);

        if(board.currentBoardState.canBlackCastleKingside) {
            if (board.getPieceOnSquare((byte) 58) == 0 && board.getPieceOnSquare((byte) 59) == 0)
                castlingMoves.add(new Move((byte) 60, (byte) 58, KING_CASTLE));
        }
        if(board.currentBoardState.canBlackCastleQueenside) {
            if (board.getPieceOnSquare((byte) 61) == 0 && board.getPieceOnSquare((byte) 62) == 0 && board.getPieceOnSquare((byte) 63) == 0)
                castlingMoves.add(new Move((byte) 60, (byte) 62, QUEEN_CASTLE));
        }

        return castlingMoves;
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

    private ArrayList<Move> deletePseudoLegalCastling(byte myColor, ArrayList<Move> possibleMoves) {
        ArrayList<Move> legalMoves = new ArrayList<>(2);

        for(Move move: possibleMoves) {
                if(myColor == WHITE) {
                    if(move.type == KING_CASTLE) {
                        int checks = 0;
                        if(board.isWhiteInCheck())
                            checks++;

                        board.makeMove(new Move((byte) 4, (byte) 3, QUIET_MOVE));
                        if(board.isWhiteInCheck()) {
                            checks++;
                        }
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);
                    }
                    else {
                        int checks = 0;
                        if(board.isWhiteInCheck())
                            checks++;

                        board.makeMove(new Move((byte) 4, (byte) 5, QUIET_MOVE));
                        if(board.isWhiteInCheck())
                            checks++;
                        board.unmakeMove();
                        board.makeMove(new Move((byte) 4, (byte) 6, QUIET_MOVE));
                        if(board.isWhiteInCheck())
                            checks++;
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);

                    }
                }
                else {
                    if(move.type == KING_CASTLE) {
                        int checks = 0;
                        if(board.isBlackInCheck())
                            checks++;

                        board.makeMove(new Move((byte) 60, (byte) 59, QUIET_MOVE));
                        if(board.isBlackInCheck()) {
                            checks++;
                        }
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);
                    }
                    else {
                        int checks = 0;
                        if(board.isBlackInCheck())
                            checks++;

                        board.makeMove(new Move((byte) 60, (byte) 61, QUIET_MOVE));
                        if(board.isBlackInCheck())
                            checks++;
                        board.unmakeMove();
                        board.makeMove(new Move((byte) 60, (byte) 62, QUIET_MOVE));
                        if(board.isBlackInCheck())
                            checks++;
                        board.unmakeMove();
                        if(checks == 0)
                            legalMoves.add(move);

                    }
                }

        }
        return legalMoves;
    }
}
