package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Search;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

import java.util.ArrayList;
import java.util.Collections;


public class MiddleSearcher implements Search {

    Board board;
    MoveGenerator moveGenerator;
    Searcher searcher;
    Evaluator evaluator;

    QuiescenceSearch quiescenceSearch;

    private static final int MINIMUM = -100_000_000;
    private static final int MAXIMUM = 100_000_000;

    public MiddleSearcher(Board board, Searcher searcher, Evaluator evaluator, MoveGenerator moveGenerator) {
        this.board = board;
        this.searcher = searcher;
        this.evaluator = evaluator;
        this.moveGenerator = moveGenerator;
        this.quiescenceSearch = new QuiescenceSearch(board, evaluator, moveGenerator, searcher);
    }

    @Override
    public void search() {
        UciSender.sendDebugMessage("Entered middle searcher");

        for(int i=1; i<255; i++) {
            evaluator.counter = 0;
            Move bestMoveInDepth = alphaBetaNegEntryPoint(i, MINIMUM, MAXIMUM);

            if(bestMoveInDepth != null)
                searcher.bestMove = bestMoveInDepth;

            if(searcher.stopSearch)
                return;

            UciSender.sendInfoMessage("depth " + i + " score cp " + evaluator.evaluate() + " pv " + searcher.bestMove + " nodes " + evaluator.getCount());
        }
    }

    private Move alphaBetaNegEntryPoint(int depth, int alpha, int beta) {

        int bestMoveValue = MINIMUM;
        Move bestMove = null;

        ArrayList<Move> moves = moveGenerator.generateAllLegalMoves();
        Collections.sort(moves);

        for(Move move : moves) {

            if(searcher.stopSearch)
                return null;

            board.makeMove(move);
            int score = -alphaBetaNeg(depth-1, -beta, -alpha);
            if(score > bestMoveValue && !searcher.stopSearch)
            {
                bestMoveValue = score;
                bestMove = move;
                if(score > alpha)
                    alpha = score;
            }

            board.unmakeMove();
            if(score >= beta)
                break;
        }
        return bestMove;
    }

    private Integer alphaBetaNeg(int depth, int alpha, int beta) {
        ArrayList<Move> moves = moveGenerator.generateAllLegalMoves();
        Collections.sort(moves);

        if(depth == 0 || moves.isEmpty()) {
            if(!Config.quiescenceSearch)
                return evaluator.evaluate();
            else
                return quiescenceSearch.search(alpha, beta);

        }

        int bestMoveValue = MINIMUM;
        for(Move move : moves) {
            if(searcher.stopSearch)
                break;

            board.makeMove(move);
            int score = -alphaBetaNeg(depth-1, -beta, -alpha);
            if(score > bestMoveValue) {
                bestMoveValue = score;
                if(score > alpha)
                    alpha = score;
            }
            board.unmakeMove();

            if(score >= beta)
                break;
        }
        return bestMoveValue;
    }


}

