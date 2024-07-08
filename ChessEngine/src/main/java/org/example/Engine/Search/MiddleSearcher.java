package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

import java.util.ArrayList;


public class MiddleSearcher implements Search {

    Board board;
    MoveGenerator moveGenerator;
    Searcher searcher;
    Evaluator evaluator;

    public MiddleSearcher(Board board, Searcher searcher, Evaluator evaluator, MoveGenerator moveGenerator) {
        this.board = board;
        this.searcher = searcher;
        this.evaluator = evaluator;
        this.moveGenerator = moveGenerator;
    }

    @Override
    public void search() {

        for(int i=1; i<256; i++) {
            Move bestMoveInDepth = alphaBetaNegEntryPoint(i, searcher);

            if(bestMoveInDepth != null)
                searcher.bestMove = bestMoveInDepth;

            if(searcher.stopSearch)
                return;

            UciSender.sendInfoMessage("depth " + i + " score cp " + evaluator.evaluate() + " pv " + searcher.bestMove);
        }


    }

    private Move alphaBetaNegEntryPoint(int depth, Searcher searcher) {

        int bestMoveValue;
        Move bestMove = null;

        bestMoveValue = Integer.MIN_VALUE;

        for(Move move : moveGenerator.generateMoves()) {

            if(searcher.stopSearch)
                return null;

            board.makeMove(move);
            int score = -alphaBetaNeg(depth-1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.unmakeMove();

            if(score > bestMoveValue) {
                bestMoveValue = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    private int alphaBetaNeg(int depth, int alpha, int beta) {
        ArrayList<Move> moves = moveGenerator.generateMoves();

        if(depth == 0 || moves.isEmpty())
            return evaluator.evaluate();

        int score = Integer.MIN_VALUE;
        for(Move move : moves) {

            if(searcher.stopSearch)
                return 0;

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
