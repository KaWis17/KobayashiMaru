package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testRunPerft() {
        String stockfish =
                "g2g3: 1\n" +
                        "h2h3: 1\n" +
                        "g1f2: 1\n" +
                        "e4e5: 1\n" +
                        "g2g4: 1\n" +
                        "h2h4: 1\n" +
                        "f3e1: 1\n" +
                        "f3d2: 1\n" +
                        "f3h4: 1\n" +
                        "f3e5: 1\n" +
                        "f3g5: 1\n" +
                        "h6g4: 1\n" +
                        "h6f5: 1\n" +
                        "h6f7: 1\n" +
                        "h6g8: 1\n" +
                        "a4c2: 1\n" +
                        "a4b3: 1\n" +
                        "b4e1: 1\n" +
                        "b4d2: 1\n" +
                        "b4a3: 1\n" +
                        "b4c3: 1\n" +
                        "b4a5: 1\n" +
                        "b4c5: 1\n" +
                        "b4d6: 1\n" +
                        "b4e7: 1\n" +
                        "b4f8: 1\n" +
                        "a1b1: 1\n" +
                        "a1c1: 1\n" +
                        "f1e1: 1\n" +
                        "f1f2: 1\n" +
                        "d1b1: 1\n" +
                        "d1c1: 1\n" +
                        "d1e1: 1\n" +
                        "d1c2: 1\n" +
                        "d1d2: 1\n" +
                        "d1e2: 1\n" +
                        "d1b3: 1\n" +
                        "d1d3: 1\n" +
                        "a8a5: 1\n" +
                        "a8a6: 1\n" +
                        "a8a7: 1\n" +
                        "a8b7: 1\n" +
                        "a8b8: 1\n" +
                        "a8c8: 1\n" +
                        "g1h1: 1";

        String myPerft =
                "g1f2: 1\n" +
                        "g1h1: 1\n" +
                        "e4e5: 1\n" +
                        "g2g3: 1\n" +
                        "h2h3: 1\n" +
                        "g2g4: 1\n" +
                        "h2h4: 1\n" +
                        "f3e5: 1\n" +
                        "f3g5: 1\n" +
                        "f3h4: 1\n" +
                        "f3d2: 1\n" +
                        "f3e1: 1\n" +
                        "h6g8: 1\n" +
                        "h6f5: 1\n" +
                        "h6g4: 1\n" +
                        "h6f7: 1\n" +
                        "f1f2: 1\n" +
                        "f1e1: 1\n" +
                        "a1b1: 1\n" +
                        "a1c1: 1\n" +
                        "b4a3: 1\n" +
                        "b4f8: 1\n" +
                        "b4e7: 1\n" +
                        "b4d6: 1\n" +
                        "b4a5: 1\n" +
                        "b4c5: 1\n" +
                        "b4c3: 1\n" +
                        "b4d2: 1\n" +
                        "b4e1: 1\n" +
                        "a4b3: 1\n" +
                        "a4c2: 1\n" +
                        "d1d3: 1\n" +
                        "d1d2: 1\n" +
                        "d1b1: 1\n" +
                        "d1c1: 1\n" +
                        "d1e1: 1\n" +
                        "a8c8: 1\n" +
                        "a8b8: 1\n" +
                        "a8a7: 1\n" +
                        "a8a6: 1\n" +
                        "a8a5: 1\n" +
                        "d1b3: 1\n" +
                        "d1c2: 1\n" +
                        "d1e2: 1\n" +
                        "a8b7: 1\n" +
                        "a8h1: 1";

        testComaprePerft(stockfish, myPerft);
    }


    private void testComaprePerft(String stockfish, String myPerft) {

        HashMap<String, Integer> stockfishMap = new HashMap<>();
        String[] stockfishLines = stockfish.split("\n");
        for (String line : stockfishLines) {
            String[] parts = line.split(": ");
            stockfishMap.put(parts[0], Integer.parseInt(parts[1]));
        }

        HashMap<String, Integer> myPerftMap = new HashMap<>();
        String[] myPerftLines = myPerft.split("\n");
        for (String line : myPerftLines) {
            String[] parts = line.split(": ");
            myPerftMap.put(parts[0], Integer.parseInt(parts[1]));
        }


        for(String key: myPerftMap.keySet()) {
            if(stockfishMap.get(key) == null) {
                System.out.println("No value for key: " + key);
                continue;
            }
            int stockfishValue = stockfishMap.get(key);
            int myPerftValue = myPerftMap.get(key);

            if(stockfishValue != myPerftValue) {
                System.out.println("Key: " + key);
                System.out.println("Stockfish: " + stockfishValue);
                System.out.println("MyPerft: " + myPerftValue);
                System.out.println("Diff: " + (stockfishValue - myPerftValue));
                System.out.println();
            }
            else{
                System.out.println("Key: " + key + " OK");
            }


        }
    }
}
