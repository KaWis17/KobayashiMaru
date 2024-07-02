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
                "e2e3: 698572\n" +
                        "g3g4: 567522\n" +
                        "c5c6: 516991\n" +
                        "e2e4: 679032\n" +
                        "b7b8q: 819285\n" +
                        "b7b8r: 742424\n" +
                        "b7b8b: 542179\n" +
                        "b7b8n: 518605\n" +
                        "d7d8q: 637639\n" +
                        "d7d8r: 583037\n" +
                        "d7d8b: 570902\n" +
                        "d7d8n: 55772\n" +
                        "c5d6: 610494\n" +
                        "h4g5: 798430\n" +
                        "g6d3: 618639\n" +
                        "g6e4: 665992\n" +
                        "g6f5: 84550\n" +
                        "g6h5: 540819\n" +
                        "g6f7: 90628\n" +
                        "g6h7: 602905\n" +
                        "g1a1: 731189\n" +
                        "g1b1: 651553\n" +
                        "g1c1: 542027\n" +
                        "g1d1: 620971\n" +
                        "g1e1: 546949\n" +
                        "g1f1: 604668\n" +
                        "g1h1: 605387\n" +
                        "g1g2: 418422\n" +
                        "c2b1: 584025\n" +
                        "c2c1: 574146\n" +
                        "c2d1: 510930\n" +
                        "c2a2: 605359\n" +
                        "c2b2: 593619\n" +
                        "c2d2: 653057\n" +
                        "c2b3: 631778\n" +
                        "c2c3: 624816\n" +
                        "c2d3: 591660\n" +
                        "c2a4: 735559\n" +
                        "c2c4: 632779\n" +
                        "c2e4: 39907\n" +
                        "c2f5: 12398\n" +
                        "e8d8: 564976";

        String myPerft =
                "e8d8: 710118\n" +
                        "e8e7: 756619\n" +
                        "c5c6: 612470\n" +
                        "g3g4: 730282\n" +
                        "e2e3: 885131\n" +
                        "b7b8n: 611159\n" +
                        "d7d8n: 122992\n" +
                        "b7b8b: 691111\n" +
                        "d7d8b: 698959\n" +
                        "b7b8r: 936442\n" +
                        "d7d8r: 718591\n" +
                        "b7b8q: 1023435\n" +
                        "d7d8q: 716013\n" +
                        "e2e4: 855808\n" +
                        "c5d6: 704705\n" +
                        "g1g2: 535370\n" +
                        "g1a1: 924347\n" +
                        "g1b1: 825502\n" +
                        "g1c1: 689281\n" +
                        "g1d1: 788593\n" +
                        "g1e1: 696744\n" +
                        "g1f1: 765599\n" +
                        "g1h1: 767769\n" +
                        "h4g5: 927539\n" +
                        "g6f7: 203181\n" +
                        "g6h7: 825521\n" +
                        "g6f5: 170502\n" +
                        "g6h5: 691061\n" +
                        "g6e4: 907385\n" +
                        "g6d3: 840102\n" +
                        "c2c4: 810569\n" +
                        "c2c3: 815551\n" +
                        "c2a2: 775540\n" +
                        "c2b2: 773033\n" +
                        "c2d2: 832438\n" +
                        "c2c1: 730450\n" +
                        "c2f5: 38729\n" +
                        "c2a4: 871578\n" +
                        "c2e4: 75331\n" +
                        "c2b3: 809671\n" +
                        "c2d3: 757044\n" +
                        "c2b1: 743299\n" +
                        "c2d1: 651797";

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
