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


public class MiddleSearcher implements Search {

    Board board;
    MoveGenerator moveGenerator;
    Searcher searcher;
    Evaluator evaluator;

    QuiescenceSearch quiescenceSearch;

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
        evaluator.getCount();
        for(int i=1; i<256; i++) {
            Move bestMoveInDepth = alphaBetaNegEntryPoint(i, searcher);

            if(bestMoveInDepth != null)
                searcher.bestMove = bestMoveInDepth;

            if(searcher.stopSearch)
                return;

            UciSender.sendInfoMessage("depth " + i + " score cp " + evaluator.evaluate() + " pv " + searcher.bestMove + " nodes " + evaluator.getCount());
        }
    }

    private Move alphaBetaNegEntryPoint(int depth, Searcher searcher) {

        int bestMoveValue = Integer.MIN_VALUE;
        Move bestMove = null;

        for(Move move : moveGenerator.generateAllLegalMoves()) {

            if(searcher.stopSearch)
                return null;

            board.makeMove(move);
            int score = -alphaBetaNeg(depth-1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.unmakeMove();

            if(score > bestMoveValue && !searcher.stopSearch) {
                bestMoveValue = score;
                bestMove = move;
            }

        }

        return bestMove;
    }

    private Integer alphaBetaNeg(int depth, int alpha, int beta) {
        ArrayList<Move> moves = moveGenerator.generateAllLegalMoves();
//        Collections.sort(moves);

        if(depth == 0 || moves.isEmpty()) {
            if(!Config.quiescenceSearch)
                return evaluator.evaluate();
            else
                return quiescenceSearch.search(alpha, beta, 3);
        }

        int score = Integer.MIN_VALUE;
        for(Move move : moves) {

            if(searcher.stopSearch)
                break;

            board.makeMove(move);
            score = Math.max(score, -alphaBetaNeg(depth-1, -beta, -alpha));
            board.unmakeMove();

            alpha = Math.max(alpha, score);
            if(alpha >= beta)
                break;
        }
        return score;
    }
}
