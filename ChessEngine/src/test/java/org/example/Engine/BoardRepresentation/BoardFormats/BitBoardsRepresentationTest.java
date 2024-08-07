package org.example.Engine.BoardRepresentation.BoardFormats;

import junit.framework.TestCase;

import static org.example.Engine.BoardRepresentation.BoardHelper.*;

public class BitBoardsRepresentationTest extends TestCase {

        public void testAddPieceOnSquare() {
            BitBoardsRepresentation bitBoards = new BitBoardsRepresentation();
            bitBoards.addPieceOnSquare((byte) 64, BLACK, PAWN);
            bitBoards.addPieceOnSquare((byte) 57, BLACK, PAWN);
            bitBoards.addPieceOnSquare((byte) 21, BLACK, PAWN);
            bitBoards.addPieceOnSquare((byte) 1, BLACK, PAWN);

            displayBitBoard(bitBoards.bitBoards[BLACK | PAWN]);
            assertEquals(-9151314442815799295L, bitBoards.bitBoards[BLACK | PAWN]);
        }

        public void displayBitBoard(long bitBoard) {
            String binaryString = Long.toBinaryString(bitBoard);
            String paddedBinaryString = String.format("%64s", binaryString).replace(' ', '0');
            System.out.println(paddedBinaryString);
        }
}