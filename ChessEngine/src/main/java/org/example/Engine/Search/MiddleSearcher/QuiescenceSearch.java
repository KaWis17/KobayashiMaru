package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;

import java.util.ArrayList;
import java.util.Collections;

public class QuiescenceSearch {

    Board board;
    Evaluator evaluator;
    MoveGenerator generator;
    Searcher searcher;

    QuiescenceSearch(Board board, Evaluator evaluator, MoveGenerator generator, Searcher searcher) {
        this.board = board;
        this.evaluator = evaluator;
        this.generator = generator;
        this.searcher = searcher;
    }

    public Integer search(int alpha, int beta, int maxDepth) {

        int standPat = evaluator.evaluate();
        if (standPat >= beta)
            return beta;
        if (alpha < standPat)
            alpha = standPat;

        ArrayList<Move> moves = generator.generateAllLegalCaptureMoves();
        Collections.sort(moves);

        for(Move move : moves) {
            if(searcher.stopSearch)
                break;

            board.makeMove(move);
            int score = -search(-beta, -alpha, maxDepth-1);
            board.unmakeMove();

            if(score >= beta)
                return beta;
            if(score > alpha)
                alpha = score;
        }
        return alpha;
    }
}
