package org.example.Engine.BoardRepresentation.BoardFormats;

import java.util.Arrays;

public class ArrayRepresentation implements BoardFormat {

    public byte[] board = new byte[64];

    @Override
    public void addPieceOnSquare(short square, short color, short piece) {
        square = (short) (64- square);
        board[square] = (byte) (color | piece);
    }

    @Override
    public void deletePieceOnSquare(short square, short color, short piece) {
        square = (short) (64- square);
        board[square] = 0;
    }

    @Override
    public void clearBoard() {
        Arrays.fill(board, (byte) 0);
    }

    public byte getPieceOnSquare(short square) {
        square = (short) (64 - square);
        return board[square];
    }
}
