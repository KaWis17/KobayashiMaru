package org.example.Engine.BoardRepresentation.BoardFormats;

import junit.framework.TestCase;

import static org.example.Engine.BoardRepresentation.BoardConstants.*;

public class ArrayRepresentationTest extends TestCase {

    public void testAddPieceOnSquare() {
        ArrayRepresentation array = new ArrayRepresentation();

        array.addPieceOnSquare((short) 28, BLACK, PAWN);
        array.deletePieceOnSquare((short) 12, BLACK, PAWN);

        displayBoard(array.board);
    }

    void displayBoard(byte[] board) {
        for (int i = 0; i < 64; i++) {
            System.out.print(board[i] + "");
        }
    }
}