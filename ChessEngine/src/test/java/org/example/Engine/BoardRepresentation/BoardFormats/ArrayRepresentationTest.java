package org.example.Engine.BoardRepresentation.BoardFormats;

import junit.framework.TestCase;

import static org.example.Engine.BoardRepresentation.BoardHelper.*;

public class ArrayRepresentationTest extends TestCase {

    public void testAddPieceOnSquare() {
        ArrayRepresentation array = new ArrayRepresentation();

        array.addPieceOnSquare((byte) 28, BLACK, PAWN);
        array.deletePieceOnSquare((byte) 12, BLACK, PAWN);

        displayBoard(array.board);
    }

    void displayBoard(byte[] board) {
        for (int i = 0; i < 64; i++) {
            System.out.print(board[i] + "");
        }
    }
}