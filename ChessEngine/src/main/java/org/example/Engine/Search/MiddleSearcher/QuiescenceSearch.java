package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class QuiescenceSearch {

    Board board;
    Evaluator evaluator;
    MoveGenerator generator;
    Searcher searcher;
    TranspositionTable transpositionTable;
    MiddleSearcher middleSearcher;

    QuiescenceSearch(Board board, Evaluator evaluator, MoveGenerator generator, Searcher searcher, TranspositionTable transpositionTable, MiddleSearcher middleSearcher) {
        this.board = board;
        this.evaluator = evaluator;
        this.generator = generator;
        this.searcher = searcher;
        this.transpositionTable = transpositionTable;
        this.middleSearcher = middleSearcher;
    }

    public Integer search(int depth, int alpha, int beta) {

        if(Config.TRANSPOSITION_TABLE_ON) {
            TranspositionResult result = transpositionTable.get(board.zobristHashing.getTranspositionHash(), depth);
            if (result != null) {
                if (result.flag == TranspositionResult.Flag.EXACT) return result.score;
                else if (result.flag == TranspositionResult.Flag.LOWER_BOUND) alpha = max(alpha, result.score);
                else if (result.flag == TranspositionResult.Flag.UPPER_BOUND) beta = min(beta, result.score);

                if (alpha >= beta) return result.score;
            }
        }

        int standPat = evaluator.evaluate(middleSearcher.currentDepthSearchStart - depth);

        if(depth == 0) {
            return standPat;
        }

        if (standPat >= beta) {
            return beta;
        }
        if (alpha < standPat)
            alpha = standPat;

        ArrayList<Move> moves = generator.generateAllPseudoLegalCaptureMoves();
        if(moves.isEmpty())
            return standPat;

        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        for(Move move : moves) {

            if(!searcher.isCurrentlyThinking)
                break;

            if(board.makeMove(move)) {
                int score = -search(depth-1, -beta, -alpha);
                board.unmakeMove();

                if(score >= beta) {
                    return beta;
                }
                if(score > alpha)
                    alpha = score;
            }
            else
                board.unmakeMove();
        }

        return alpha;
    }
}
