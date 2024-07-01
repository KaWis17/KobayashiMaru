package org.example.Engine.MoveGeneration;

public interface BitBoardConstants {

    long[] ranks = {
            Long.parseLong("0000000000000000000000000000000000000000000000000000000011111111", 2),
            Long.parseLong("0000000000000000000000000000000000000000000000001111111100000000", 2),
            Long.parseLong("0000000000000000000000000000000000000000111111110000000000000000", 2),
            Long.parseLong("0000000000000000000000000000000011111111000000000000000000000000", 2),
            Long.parseLong("0000000000000000000000001111111100000000000000000000000000000000", 2),
            Long.parseLong("0000000000000000111111110000000000000000000000000000000000000000", 2),
            Long.parseLong("0000000011111111000000000000000000000000000000000000000000000000", 2),
            Long.parseLong("111111110000000000000000000000000000000000000000000000000000000", 2) * 2
    };

    long rank1 = ranks[0];
    long rank2 = ranks[1];
    long rank3 = ranks[2];
    long rank4 = ranks[3];
    long rank5 = ranks[4];
    long rank6 = ranks[5];
    long rank7 = ranks[6];
    long rank8 = ranks[7];

    long[] files = {
            Long.parseLong("0000000100000001000000010000000100000001000000010000000100000001", 2),
            Long.parseLong("0000001000000010000000100000001000000010000000100000001000000010", 2),
            Long.parseLong("0000010000000100000001000000010000000100000001000000010000000100", 2),
            Long.parseLong("0000100000001000000010000000100000001000000010000000100000001000", 2),
            Long.parseLong("0001000000010000000100000001000000010000000100000001000000010000", 2),
            Long.parseLong("0010000000100000001000000010000000100000001000000010000000100000", 2),
            Long.parseLong("0100000001000000010000000100000001000000010000000100000001000000", 2),
            Long.parseLong("100000001000000010000000100000001000000010000000100000001000000", 2) * 2
    };

    long fileA = files[7];
    long fileB = files[6];
    long fileC = files[5];
    long fileD = files[4];
    long fileE = files[3];
    long fileF = files[2];
    long fileG = files[1];
    long fileH = files[0];

    long[] majorDiagonals = {
            Long.parseLong("0000000100000000000000000000000000000000000000000000000000000000", 2),
            Long.parseLong("0000001000000001000000000000000000000000000000000000000000000000", 2),
            Long.parseLong("0000010000000010000000010000000000000000000000000000000000000000", 2),
            Long.parseLong("0000100000000100000000100000000100000000000000000000000000000000", 2),
            Long.parseLong("0001000000001000000001000000001000000001000000000000000000000000", 2),
            Long.parseLong("0010000000010000000010000000010000000010000000010000000000000000", 2),
            Long.parseLong("0100000000100000000100000000100000000100000000100000000100000000", 2),
            Long.parseLong("100000000100000000100000000100000000100000000100000000100000000", 2) * 2 + 1,
            Long.parseLong("0000000010000000010000000010000000010000000010000000010000000010", 2),
            Long.parseLong("0000000000000000100000000100000000100000000100000000100000000100", 2),
            Long.parseLong("0000000000000000000000001000000001000000001000000001000000001000", 2),
            Long.parseLong("0000000000000000000000000000000010000000010000000010000000010000", 2),
            Long.parseLong("0000000000000000000000000000000000000000100000000100000000100000", 2),
            Long.parseLong("0000000000000000000000000000000000000000000000001000000001000000", 2),
            Long.parseLong("0000000000000000000000000000000000000000000000000000000010000000", 2),
    };


    long[] minorDiagonals = {
            Long.parseLong("0000000000000000000000000000000000000000000000000000000000000001", 2),
            Long.parseLong("0000000000000000000000000000000000000000000000000000000100000010", 2),
            Long.parseLong("0000000000000000000000000000000000000000000000010000001000000100", 2),
            Long.parseLong("0000000000000000000000000000000000000001000000100000010000001000", 2),
            Long.parseLong("0000000000000000000000000000000100000010000001000000100000010000", 2),
            Long.parseLong("0000000000000000000000010000001000000100000010000001000000100000", 2),
            Long.parseLong("0000000000000001000000100000010000001000000100000010000001000000", 2),
            Long.parseLong("0000000100000010000001000000100000010000001000000100000010000000", 2),
            Long.parseLong("0000001000000100000010000001000000100000010000001000000000000000", 2),
            Long.parseLong("0000010000001000000100000010000001000000100000000000000000000000", 2),
            Long.parseLong("0000100000010000001000000100000010000000000000000000000000000000", 2),
            Long.parseLong("0001000000100000010000001000000000000000000000000000000000000000", 2),
            Long.parseLong("0010000001000000100000000000000000000000000000000000000000000000", 2),
            Long.parseLong("0100000010000000000000000000000000000000000000000000000000000000", 2),
            Long.parseLong("100000000000000000000000000000000000000000000000000000000000000", 2) * 2
    };
}
