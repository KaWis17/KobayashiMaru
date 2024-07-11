package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class BitBoardsRepresentation implements Format {

    public long[] bitBoards = new long[15];

    @Override
    public void addPieceOnSquare(byte square, byte color, byte piece) {
        bitBoards[piece | color] |= Long.rotateLeft(1L, square-1);
        bitBoards[color] |= Long.rotateLeft(1L, square-1);
    }

    @Override
    public void deletePieceOnSquare(byte square, byte color, byte piece) {
        bitBoards[piece | color] &= ~Long.rotateLeft(1L, square-1);
        bitBoards[color] &= ~Long.rotateLeft(1L, square-1);
    }

    @Override
    public void clearBoard() {
        Arrays.fill(bitBoards, 0L);
    }
}
