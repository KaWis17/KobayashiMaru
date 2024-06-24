package org.example.Engine.MoveGeneration;

public interface Constants {


    long fileH = Long.parseLong("0000000100000001000000010000000100000001000000010000000100000001", 2);
    long fileG = fileH << 1;
    long fileF = fileH << 2;
    long fileE = fileH << 3;
    long fileD = fileH << 4;
    long fileC = fileH << 5;
    long fileB = fileH << 6;
    long fileA = fileH << 7;

    long rank1 = Long.parseLong("0000000000000000000000000000000000000000000000000000000011111111", 2);
    long rank2 = rank1 << 8;
    long rank3 = rank1 << 2*8;
    long rank4 = rank1 << 3*8;
    long rank5 = rank1 << 4*8;
    long rank6 = rank1 << 5*8;
    long rank7 = rank1 << 6*8;
    long rank8 = rank1 << 7*8;

    long emptyBinary = Long.parseLong("0000000000000000000000000000000000000000000000000000000000000000", 2);


}
