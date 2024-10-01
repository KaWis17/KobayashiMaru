package org.example.Engine.MoveGeneration;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.BoardHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PerftTestTest extends TestCase {

    public void testPerftWithPositions() {
//        System.out.println("Test perft for starting position");
//        perftFromStartPosition(BoardHelper.STARTING_FEN);

        System.out.println("Test perft for starting position 1");
        perftFromPosition1("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");

        System.out.println("Test perft for starting position 2");
        perftFromPosition2("8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1");

        System.out.println("Test perft for starting position 3a");
        perftFrmPosition3("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        System.out.println("Test perft for starting position 3b");
        perftFrmPosition3("r2q1rk1/pP1p2pp/Q4n2/bbp1p3/Np6/1B3NBn/pPPP1PPP/R3K2R b KQ - 0 1");

        System.out.println("Test perft for starting position 4");
        perftFrmPosition4("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8");

        System.out.println("Test perft for starting position 5");
        perftFrmPosition5("r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10");
    }

    private void testPositionsWithFen(String fen, int depth, long expected) {
            assertEquals(expected, PerftTest.perft(fen, depth));
    }

    private void perftFromStartPosition(String fen) {

        testPositionsWithFen(fen, 1, 20L);
        testPositionsWithFen(fen, 2, 400L);
        testPositionsWithFen(fen, 3, 8_902L);
        testPositionsWithFen(fen, 4, 197_281L);
        testPositionsWithFen(fen, 5, 4_865_609L);
        testPositionsWithFen(fen, 6, 119_060_324L);

    }

    private void perftFromPosition1(String fen) {

        testPositionsWithFen(fen, 1, 48L);
        testPositionsWithFen(fen, 2, 2039L);
        testPositionsWithFen(fen, 3, 97_862L);
        testPositionsWithFen(fen, 4, 4_085_603L);

    }

    private void perftFromPosition2(String fen) {

        testPositionsWithFen(fen, 1, 14L);
        testPositionsWithFen(fen, 2, 191L);
        testPositionsWithFen(fen, 3, 2_812L);
        testPositionsWithFen(fen, 4, 43_238L);
        testPositionsWithFen(fen, 5, 674_624L);
        testPositionsWithFen(fen, 6, 11_030_083L);

    }

    private void perftFrmPosition3(String fen) {

        testPositionsWithFen(fen, 1, 6L);
        testPositionsWithFen(fen, 2, 264L);
        testPositionsWithFen(fen, 3, 9_467L);
        testPositionsWithFen(fen, 4, 422_333L);
        testPositionsWithFen(fen, 5, 15_833_292L);

    }

    private void perftFrmPosition4(String fen) {

        testPositionsWithFen(fen, 1, 44L);
        testPositionsWithFen(fen, 2, 1_486L);
        testPositionsWithFen(fen, 3, 62_379L);
        testPositionsWithFen(fen, 4, 	2_103_487L);

    }

    private void perftFrmPosition5(String fen) {

        testPositionsWithFen(fen, 1, 46L);
        testPositionsWithFen(fen, 2, 2_079L);
        testPositionsWithFen(fen, 3, 89_890L);
        testPositionsWithFen(fen, 4, 	3_894_594L);

    }
    // position fen 4K3/1P1P4/3pk1Br/2Pp2p1/7B/6P1/2Q1Pr2/6R1 w - - 0 1 moves d7d8b
    public void testForMe() {
        PerftTest.perft("3BK3/1P6/3pk1Br/2Pp2p1/7B/6P1/2Q1Pr2/6R1 b - - 0 1", 4);
    }

    public void testFromFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/kawis/Developer/KobayashiMaru/ChessEngine/src/test/java/org/example/Engine/MoveGeneration/PieceGenerators/testingFens"));
            String line = reader.readLine();

            while (line != null) {
                String[] parts = line.split(" ! ");
                String fen = parts[0];
                int depth = Integer.parseInt(parts[1]);
                long expected = Long.parseLong(parts[2]);

                testPositionsWithFen(fen, depth, expected);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
