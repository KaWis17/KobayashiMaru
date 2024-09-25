package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.StateEvaluation.Evaluator;

import java.util.ArrayList;

public class QuiescenceSearch {

    Board board;
    Evaluator evaluator;
    MoveGenerator generator;
    int timeEntered;

    QuiescenceSearch(Board board, Evaluator evaluator, MoveGenerator generator) {
        this.board = board;
        this.evaluator = evaluator;
        this.generator = generator;
        timeEntered = 0;
    }

    public Integer search(int alpha, int beta) {
        timeEntered++;
        int stand_pat = evaluator.evaluate();
        if (stand_pat >= beta)
            return beta;

        if(alpha < stand_pat)
            alpha = stand_pat;

        ArrayList<Move> moves = generator.generateAllLegalCaptureMoves();

        for (Move move : moves)  {
            board.makeMove(move);

            int score = -search(-beta, -alpha);

            board.unmakeMove();

            if(score >= beta)
                return beta;

            if(score > alpha)
                alpha = score;
        }

        return alpha;
    }
}
