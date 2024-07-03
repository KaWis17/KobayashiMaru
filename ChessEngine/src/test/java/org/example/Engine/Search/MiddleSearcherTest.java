package org.example.Engine.Search;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.Engine;
import org.example.Engine.StateEvaluation.Evaluation;
import org.example.Engine.StateEvaluation.Evaluator;

public class MiddleSearcherTest extends TestCase {

    public void testSearch() {
        Board board = new Board();
        board.startFromCustomPosition("r4rk1/p1pqpppp/2ppbn2/4P3/8/1PNP1N2/P1P2PPP/R1BQK2R b KQ - 0 8");

        Searcher searcher = new Searcher(new Engine(), board, null);
        Evaluator evaluator = new Evaluator(board);
        MiddleSearcher middleSearcher = new MiddleSearcher(board, searcher, evaluator);
        middleSearcher.search();
    }
}