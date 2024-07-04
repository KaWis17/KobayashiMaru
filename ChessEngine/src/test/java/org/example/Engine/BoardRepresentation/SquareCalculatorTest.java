package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;

public class SquareCalculatorTest extends TestCase {

    public void testCalculate() {
        assertEquals(BoardConstants.calculate("a8"), 64);
        assertEquals(BoardConstants.calculate("a1"), 8);
        assertEquals(BoardConstants.calculate("h1"), 1);
        assertEquals(BoardConstants.calculate("h8"), 57);
        assertEquals(BoardConstants.calculate("c4"), 30);
        assertEquals(BoardConstants.calculate("f6"), 43);
    }

    public void testCalculate2() {
        assertEquals(BoardConstants.calculate((short) 64), "a8");
        assertEquals(BoardConstants.calculate((short) 8), "a1");
        assertEquals(BoardConstants.calculate((short) 1), "h1");
        assertEquals(BoardConstants.calculate((short) 57), "h8");
        assertEquals(BoardConstants.calculate((short) 30), "c4");
        assertEquals(BoardConstants.calculate((short) 43), "f6");
    }

}