package org.example.Engine.BoardRepresentation;

import junit.framework.TestCase;

public class SquareCalculatorTest extends TestCase {

    public void testCalculate() {
        assertEquals(SquareCalculator.calculate("a8"), 64);
        assertEquals(SquareCalculator.calculate("a1"), 8);
        assertEquals(SquareCalculator.calculate("h1"), 1);
        assertEquals(SquareCalculator.calculate("h8"), 57);
        assertEquals(SquareCalculator.calculate("c4"), 30);
        assertEquals(SquareCalculator.calculate("f6"), 43);
    }

    public void testCalculate2() {
        assertEquals(SquareCalculator.calculate((short) 64), "a8");
        assertEquals(SquareCalculator.calculate((short) 8), "a1");
        assertEquals(SquareCalculator.calculate((short) 1), "h1");
        assertEquals(SquareCalculator.calculate((short) 57), "h8");
        assertEquals(SquareCalculator.calculate((short) 30), "c4");
        assertEquals(SquareCalculator.calculate((short) 43), "f6");
    }

}