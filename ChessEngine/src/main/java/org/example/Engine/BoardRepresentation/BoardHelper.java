package org.example.Engine.BoardRepresentation;

import java.util.HashMap;
import java.util.Map;

public interface BoardHelper {
    enum GAME_STATE {EARLY_GAME, MID_GAME, END_GAME, WIN, DRAW, LOSS}

    String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    byte WHITE = 0;
    byte BLACK = 8;

    byte PAWN = 1;
    byte BISHOP = 2;
    byte KNIGHT = 3;
    byte ROOK = 4;
    byte QUEEN = 5;
    byte KING = 6;

    static byte getPieceType(byte piece) {
        return (byte) (piece & 7);
    }

    static byte getPieceColor(byte piece) {
        return (byte) (piece & 8);
    }

    static String squareNumberToString(byte square) {
        StringBuilder sb = new StringBuilder();
        switch(square%8) {
            case 0 -> sb.append("a");
            case 7 -> sb.append("b");
            case 6 -> sb.append("c");
            case 5 -> sb.append("d");
            case 4 -> sb.append("e");
            case 3 -> sb.append("f");
            case 2 -> sb.append("g");
            case 1 -> sb.append("h");
        }
        sb.append((square-1)/8+1);

        return sb.toString();
    }

    static byte squareStringToNumber(String square) {
        if(square.equals("-") || square.equals("0"))
            return 0;

        byte value = (byte) (8*rowValue(square));
        value += columnValue(square);

        return value;
    }



    private static byte rowValue(String square) {
        return (byte) (Character.getNumericValue(square.charAt(1)) - 1);
    }

    private static byte columnValue(String square) {
        return switch (square.charAt(0)) {
            case 'a' -> 8;
            case 'b' -> 7;
            case 'c' -> 6;
            case 'd' -> 5;
            case 'e' -> 4;
            case 'f' -> 3;
            case 'g' -> 2;
            case 'h' -> 1;
            default -> 0;
        };
    }

    static void FENToBoard(Board board, String fen) {
        String[] fenSplit = fen.split(" ", 2);

        board.currentBoardState = new State();

        board.currentBoardState.gameState = GAME_STATE.EARLY_GAME;
        board.positionCount = new HashMap<>();

        fillValues(board, fenSplit[1]);
        fillFormats(board, fenSplit[0]);
        board.currentBoardState.FEN = getBoardFen(board);
    }

    static String BoardToFEN(Board board) {
        return getBoardFen(board) + " " + getValuesFen(board);
    }

    static String BoardToLibraryFEN(Board board) {
        return getBoardFen(board) + " " + getShortValuesFen(board);
    }

    private static void fillValues(Board board, String leftoverFen) {
        String[] leftoverSplit = leftoverFen.split(" ");

        State boardState = board.currentBoardState;

        boardState.whiteToMove = (leftoverSplit[0].equals("w"));

        boardState.canWhiteCastleKingSide = (leftoverSplit[1].contains("K"));
        boardState.canWhiteCastleQueenSide = (leftoverSplit[1].contains("Q"));
        boardState.canBlackCastleKingSide = (leftoverSplit[1].contains("k"));
        boardState.canBlackCastleQueenSide = (leftoverSplit[1].contains("q"));

        boardState.enPassantTarget = BoardHelper.squareStringToNumber(leftoverSplit[2]);

        boardState.halfMoveClock = Byte.parseByte(leftoverSplit[3]);
        boardState.fullMoveNumber = Byte.parseByte(leftoverSplit[4]);

        boardState.moveThatTookToThisPosition = null;
    }


    private static void fillFormats(Board board, String boardFen) {
        board.clearBoard();

        byte squareNumber = 64;

        for(char c: boardFen.toCharArray()) {

            if(Character.isDigit(c))
                squareNumber -= (byte) (Character.getNumericValue(c));

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

    Map<Integer, Character> PIECE_TO_FEN = Map.ofEntries(
            Map.entry(WHITE | PAWN, 'P'),
            Map.entry(BLACK | PAWN, 'p'),
            Map.entry(WHITE | BISHOP, 'B'),
            Map.entry(BLACK | BISHOP, 'b'),
            Map.entry(WHITE | KNIGHT, 'N'),
            Map.entry(BLACK | KNIGHT, 'n'),
            Map.entry(WHITE | ROOK, 'R'),
            Map.entry(BLACK | ROOK, 'r'),
            Map.entry(WHITE | QUEEN, 'Q'),
            Map.entry(BLACK | QUEEN, 'q'),
            Map.entry(WHITE | KING, 'K'),
            Map.entry(BLACK | KING, 'k')
    );

    static String getBoardFen(Board board) {
        StringBuilder fen = new StringBuilder();
        for(byte i=64; i>=1; i-=8) {
            int counter = 0;
            for(byte j=i; j>i-8; j--) {
                int piece = board.getPieceOnSquare(j);
                if (piece == 0) {
                    counter++;
                } else {
                    if(counter != 0){
                        fen.append(counter);
                        counter = 0;
                    }
                    fen.append(PIECE_TO_FEN.get(piece));
                }
            }
            if(counter!=0) fen.append(counter);
            fen.append("/");
        }
        fen.deleteCharAt(fen.length()-1);
        return fen.toString();
    }

    private static String getValuesFen(Board board){
        StringBuilder fen = new StringBuilder();

        State boardState = board.currentBoardState;

        if(board.isWhiteToPlay()) fen.append("w ");
        else fen.append("b ");

        fen.append((boardState.canWhiteCastleKingSide || boardState.canWhiteCastleQueenSide || boardState.canBlackCastleKingSide || boardState.canBlackCastleQueenSide) ? "" : "-" );
        if(boardState.canWhiteCastleKingSide) fen.append("K");
        if(boardState.canWhiteCastleQueenSide) fen.append("Q");
        if(boardState.canBlackCastleKingSide) fen.append("k");
        if(boardState.canBlackCastleQueenSide) fen.append("q");

        if(boardState.enPassantTarget == 0) fen.append(" - ");
        else fen.append(" ").append(BoardHelper.squareNumberToString(boardState.enPassantTarget)).append(" ");

        fen.append(boardState.halfMoveClock).append(" ");
        fen.append(boardState.fullMoveNumber);

        return fen.toString();
    }

    private static String getShortValuesFen(Board board){
        StringBuilder fen = new StringBuilder();

        State boardState = board.currentBoardState;

        if(board.isWhiteToPlay()) fen.append("w ");
        else fen.append("b ");

        fen.append((boardState.canWhiteCastleKingSide || boardState.canWhiteCastleQueenSide || boardState.canBlackCastleKingSide || boardState.canBlackCastleQueenSide) ? "" : "-" );
        if(boardState.canWhiteCastleKingSide) fen.append("K");
        if(boardState.canWhiteCastleQueenSide) fen.append("Q");
        if(boardState.canBlackCastleKingSide) fen.append("k");
        if(boardState.canBlackCastleQueenSide) fen.append("q");

        fen.append(" -");

        return fen.toString();
    }
}

/*
    a    b    c    d    e    f    g    h
  +----+----+----+----+----+----+----+----+
8 | 64 | 63 | 62 | 61 | 60 | 59 | 58 | 57 | 8
  +----+----+----+----+----+----+----+----+
7 | 56 | 55 | 54 | 53 | 52 | 51 | 50 | 49 | 7
  +----+----+----+----+----+----+----+----+
6 | 48 | 47 | 46 | 45 | 44 | 43 | 42 | 41 | 6
  +----+----+----+----+----+----+----+----+
5 | 40 | 39 | 38 | 37 | 36 | 35 | 34 | 33 | 5
  +----+----+----+----+----+----+----+----+
4 | 32 | 31 | 30 | 29 | 28 | 27 | 26 | 25 | 4
  +----+----+----+----+----+----+----+----+
3 | 24 | 23 | 22 | 21 | 20 | 19 | 18 | 17 | 3
  +----+----+----+----+----+----+----+----+
2 | 16 | 15 | 14 | 13 | 12 | 11 | 10 |  9 | 2
  +----+----+----+----+----+----+----+----+
1 |  8 |  7 |  6 |  5 |  4 |  3 |  2 |  1 | 1
  +----+----+----+----+----+----+----+----+
    a    b    c    d    e    f    g    h

 */

