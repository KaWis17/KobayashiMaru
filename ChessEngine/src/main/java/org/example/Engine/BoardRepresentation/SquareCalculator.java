package org.example.Engine.BoardRepresentation;

public class SquareCalculator {
    public static String calculate(short square) {
        return "";
    }

    public static short calculate(String square) {
        if(square.equals("-") || square.equals("0"))
            return 0;

        short value = rowValue(square);
        value += columnValue(square);

        return value;
    }

    private static short rowValue(String square) {
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

    private static short columnValue(String square) {
        if(square.contains("a")) return 1;
        if(square.contains("b")) return 2;
        if(square.contains("c")) return 3;
        if(square.contains("d")) return 4;
        if(square.contains("e")) return 5;
        if(square.contains("f")) return 6;
        if(square.contains("g")) return 7;
        if(square.contains("h")) return 8;
        return 0;
    }
}

/*

    a    b    c    d    e    f    g    h
  +----+----+----+----+----+----+----+----+
8 | 64 | 63 | 62 | 61 | 60 | 59 | 58 | 57 | 1
  +----+----+----+----+----+----+----+----+
7 | 56 | 55 | 54 | 53 | 52 | 51 | 50 | 49 | 2
  +----+----+----+----+----+----+----+----+
6 | 48 | 47 | 46 | 45 | 44 | 43 | 42 | 41 | 3
  +----+----+----+----+----+----+----+----+
5 | 40 | 39 | 38 | 37 | 36 | 35 | 34 | 33 | 4
  +----+----+----+----+----+----+----+----+
4 | 32 | 31 | 30 | 29 | 28 | 27 | 26 | 25 | 5
  +----+----+----+----+----+----+----+----+
3 | 24 | 23 | 22 | 21 | 20 | 19 | 18 | 17 | 6
  +----+----+----+----+----+----+----+----+
2 | 16 | 15 | 14 | 13 | 12 | 11 | 10 |  9 | 7
  +----+----+----+----+----+----+----+----+
1 |  8 |  7 |  6 |  5 |  4 |  3 |  2 |  1 | 8
  +----+----+----+----+----+----+----+----+
    a    b    c    d    e    f    g    h


 */
