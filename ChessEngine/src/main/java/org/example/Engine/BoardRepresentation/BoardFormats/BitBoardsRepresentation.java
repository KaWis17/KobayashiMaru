package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class BitBoardsRepresentation implements BoardFormat {

    public long[] bitBoards = new long[15];

    @Override
    public void addPieceOnSquare(short square, short color, short piece) {
        bitBoards[piece | color] |= (1L << square-1);
        bitBoards[color] |= (1L << square-1);
    }

    @Override
    public void deletePieceOnSquare(short square, short color, short piece) {
        bitBoards[piece | color] &= ~(1L << square-1);
        bitBoards[color] &= ~(1L << square-1);
    }

    @Override
    public void clearBoard() {
        Arrays.fill(bitBoards, 0L);
    }

    public String getBitboardString(short color, short piece) {

        return String.format("%64s", Long.toBinaryString(bitBoards[piece | color])).replace(' ', '0');

    }
}
