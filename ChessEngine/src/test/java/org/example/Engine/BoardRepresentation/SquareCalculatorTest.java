package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;

public class SquareCalculatorTest extends TestCase {

    public void testCalculate() {
        assertEquals(BoardHelper.squareStringToNumber("a8"), 64);
        assertEquals(BoardHelper.squareStringToNumber("a1"), 8);
        assertEquals(BoardHelper.squareStringToNumber("h1"), 1);
        assertEquals(BoardHelper.squareStringToNumber("h8"), 57);
        assertEquals(BoardHelper.squareStringToNumber("c4"), 30);
        assertEquals(BoardHelper.squareStringToNumber("f6"), 43);
    }

    public void testCalculate2() {
        assertEquals(BoardHelper.squareNumberToString((byte) 64), "a8");
        assertEquals(BoardHelper.squareNumberToString((byte) 8), "a1");
        assertEquals(BoardHelper.squareNumberToString((byte) 1), "h1");
        assertEquals(BoardHelper.squareNumberToString((byte) 57), "h8");
        assertEquals(BoardHelper.squareNumberToString((byte) 30), "c4");
        assertEquals(BoardHelper.squareNumberToString((byte) 43), "f6");
    }

}