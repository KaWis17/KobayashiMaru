package org.example.Engine.BoardRepresentation;

public class SquareCalculator {
    public static String calculate(short square) {
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

    public static byte calculate(String square) {
        if(square.equals("-") || square.equals("0"))
            return 0;

        byte value = (byte) (8*rowValue(square));
        value += columnValue(square);

        return value;
    }

    private static byte rowValue(String square) {
        if(square.contains("8")) return 7;
        if(square.contains("7")) return 6;
        if(square.contains("6")) return 5;
        if(square.contains("5")) return 4;
        if(square.contains("4")) return 3;
        if(square.contains("3")) return 2;
        if(square.contains("2")) return 1;
        if(square.contains("1")) return 0;
        return 0;
    }


    private static byte columnValue(String square) {
        if(square.contains("a")) return 8;
        if(square.contains("b")) return 7;
        if(square.contains("c")) return 6;
        if(square.contains("d")) return 5;
        if(square.contains("e")) return 4;
        if(square.contains("f")) return 3;
        if(square.contains("g")) return 2;
        if(square.contains("h")) return 1;
        return 0;
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

0, 8, 16 -> 7
1, 9, 17 -> 6
2, 10, 18 -> 5
3, 11, 19 -> 4
4, 12, 20 -> 3
5, 13, 21 -> 2
6, 14, 22 -> 1
7, 15, 23 -> 0
 */
