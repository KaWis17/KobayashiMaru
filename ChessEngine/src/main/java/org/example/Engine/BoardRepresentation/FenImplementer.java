package org.example.Engine.BoardRepresentation;

import java.util.Arrays;

public class FenImplementer implements BoardConstants{

    public static void implement(Board board, String fen) {
        String[] fenSplit = fen.split(" ", 2);

        fillValues(board, fenSplit[1]);
        fillBitBoards(board, fenSplit[0]);
    }

    public static String generateFEN(Board board) {
        //TODO currently unsupported
        return "";
    }

    private static void fillValues(Board board, String leftoverFen) {
        String[] leftoverSplit = leftoverFen.split(" ");

        board.whiteToMove = (leftoverSplit[0].equals("w"));

        board.canWhiteCastleKingside = (leftoverSplit[1].contains("K"));
        board.canWhiteCastleQueenside = (leftoverSplit[1].contains("Q"));
        board.canBlackCastleKingside = (leftoverSplit[1].contains("k"));
        board.canBlackCastleQueenside = (leftoverSplit[1].contains("q"));

        board.enPassantTarget = SquareCalculator.calculate(leftoverSplit[2]);

        board.halfMoveClock = Short.parseShort(leftoverSplit[3]);
        board.fullMoveNumber = Short.parseShort(leftoverSplit[4]);
    }


    private static void fillBitBoards(Board board, String boardFen) {
        Arrays.fill(board.bitBoards, 0L);

        short squareNumber = 64;

        for(char c: boardFen.toCharArray()) {

            if(Character.isDigit(c))
                squareNumber -= (short) (Character.getNumericValue(c));

            else if(c != '/') {
                switch (c) {
                    case 'P' -> board.addPieceOnSquare(squareNumber, WHITE, PAWN);
                    case 'p' -> board.addPieceOnSquare(squareNumber, BLACK, PAWN);
                    case 'B' -> board.addPieceOnSquare(squareNumber, WHITE, BISHOP);
                    case 'b' -> board.addPieceOnSquare(squareNumber, BLACK, BISHOP);
                    case 'N' -> board.addPieceOnSquare(squareNumber, WHITE, KNIGHT);
                    case 'n' -> board.addPieceOnSquare(squareNumber, BLACK, KNIGHT);
                    case 'R' -> board.addPieceOnSquare(squareNumber, WHITE, ROOK);
                    case 'r' -> board.addPieceOnSquare(squareNumber, BLACK, ROOK);
                    case 'Q' -> board.addPieceOnSquare(squareNumber, WHITE, QUEEN);
                    case 'q' -> board.addPieceOnSquare(squareNumber, BLACK, QUEEN);
                    case 'K' -> board.addPieceOnSquare(squareNumber, WHITE, KING);
                    case 'k' -> board.addPieceOnSquare(squareNumber, BLACK, KING);
                }
                squareNumber--;
            }
        }
    }
}
