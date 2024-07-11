package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class CountRepresentation implements Format {

    public byte[] pieces = new byte[16];

    @Override
    public void addPieceOnSquare(byte square, byte color, byte piece) {
        pieces[color | piece]++;
    }

    @Override
    public void deletePieceOnSquare(byte square, byte color, byte piece) {
        pieces[color | piece]--;
    }

    @Override
    public void clearBoard() {
        Arrays.fill(pieces, (byte) 0);
    }
}
