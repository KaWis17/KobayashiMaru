package org.example.Engine.BoardRepresentation;

public interface BoardHelper {
    enum GAME_STATE {EARLY_GAME, MID_GAME, END_GAME, WIN, DRAW, LOSS}

    String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    short WHITE = 0;
    short BLACK = 8;

    short PAWN = 1;
    short BISHOP = 2;
    short KNIGHT = 3;
    short ROOK = 4;
    short QUEEN = 5;
    short KING = 6;

    static short getPieceType(short piece) {
        return (short) (piece & 7);
    }

    static short getPieceColor(short piece) {
        return (short) (piece & 8);
    }

    static String squareNumberToString(short square) {
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

    static short squareStringToNumber(String square) {
        if(square.equals("-") || square.equals("0"))
            return 0;

        short value = (byte) (8*rowValue(square));
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

