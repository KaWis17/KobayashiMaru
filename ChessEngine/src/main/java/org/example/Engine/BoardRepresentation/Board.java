package org.example.Engine.BoardRepresentation;

public class Board implements BoardConstants{

    public BoardConstants.GAME_STATE state;
    long[] bitBoards = new long[15];
    boolean whiteToMove;
    boolean canWhiteCastleKingside, canWhiteCastleQueenside;
    boolean canBlackCastleKingside, canBlackCastleQueenside;
    short enPassantTarget;
    short halfMoveClock;
    short fullMoveNumber;

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardConstants.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        FenImplementer.implement(this, fen);
    }

    public void makeMove(Move moveToMake, boolean saveMove) {

    }

    void addPieceOnSquare(short square, short color, short piece) {
        bitBoards[piece | color] |= (1L << square-1);
        bitBoards[color] |= (1L << square-1);
    }

    void deletePieceFromSquare(short square, short color, short piece) {
        bitBoards[piece | color] &= ~(1L << square);
        bitBoards[color] &= ~(1L << square);
    }

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("STATE: \n");
        if(whiteToMove) sb.append("White on the move\n");
        else sb.append("Black on the move\n");
        for(short i=1; i<=64; ++i) {
            if(isPieceOnSquare(WHITE, PAWN, i)) sb.append("P ");
            else if(isPieceOnSquare(BLACK, PAWN, i)) sb.append("p ");
            else if(isPieceOnSquare(WHITE, BISHOP, i)) sb.append("B ");
            else if(isPieceOnSquare(BLACK, BISHOP, i)) sb.append("b ");
            else if(isPieceOnSquare(WHITE, KNIGHT, i)) sb.append("N ");
            else if(isPieceOnSquare(BLACK, KNIGHT, i)) sb.append("n ");
            else if(isPieceOnSquare(WHITE, ROOK, i)) sb.append("R ");
            else if(isPieceOnSquare(BLACK, ROOK, i)) sb.append("r ");
            else if(isPieceOnSquare(WHITE, QUEEN, i)) sb.append("Q ");
            else if(isPieceOnSquare(BLACK, QUEEN, i)) sb.append("q ");
            else if(isPieceOnSquare(WHITE, KING, i)) sb.append("K ");
            else if(isPieceOnSquare(BLACK, KING, i)) sb.append("k ");
            else
                sb.append("_ ");

            if(i % 8 == 0) sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isPieceOnSquare(short color, short type, short square) {
        String s  = String.format("%64s", Long.toBinaryString(bitBoards[color | type]));
        s = s.replace(' ', '0');
        return s.charAt(square-1) == '1';
    }

}
