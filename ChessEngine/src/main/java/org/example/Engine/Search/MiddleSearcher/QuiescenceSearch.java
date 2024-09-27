package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;

import java.util.ArrayList;

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

        ArrayList<Move> moves = generator.generateAllLegalCaptureMoves();

        if(moves.isEmpty() || maxDepth == 0)
            return evaluator.evaluate();

        int score = Integer.MIN_VALUE;

        for(Move move : moves) {
            if(searcher.stopSearch)
                break;

            board.makeMove(move);
            score = Math.max(score, -search(-beta, -alpha, maxDepth-1));
            board.unmakeMove();

            alpha = Math.max(alpha, score);
            if(alpha >= beta)
                break;
        }

        return alpha;
    }
}
