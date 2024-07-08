package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class CountRepresentation implements Format {

    public short[] pieces = new short[16];

    @Override
    public void addPieceOnSquare(short square, short color, short piece) {
        pieces[color | piece]++;
    }

    @Override
    public void deletePieceOnSquare(short square, short color, short piece) {
        pieces[color | piece]--;
    }

    @Override
    public void clearBoard() {
        Arrays.fill(pieces, (short) 0);
    }
}
