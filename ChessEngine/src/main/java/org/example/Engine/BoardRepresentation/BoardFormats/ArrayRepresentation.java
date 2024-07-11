package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class ArrayRepresentation implements Format {

    public byte[] board = new byte[64];

    @Override
    public void addPieceOnSquare(byte square, byte color, byte piece) {
        square = (byte) (64 - square);
        board[square] = (byte) (color | piece);
    }

    @Override
    public void deletePieceOnSquare(byte square, byte color, byte piece) {
        square = (byte) (64 - square);
        board[square] = 0;
    }

    @Override
    public void clearBoard() {
        Arrays.fill(board, (byte) 0);
    }

    public byte getPieceOnSquare(byte square) {
        square = (byte) (64 - square);
        return board[square];
    }
}
