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
    TranspositionTable transpositionTable = new TranspositionTable();
    private static final int MINIMUM = -100_000_000;
    private static final int MAXIMUM = 100_000_000;

    private static final int DEPTH_FOR_QUIESCENCE = 100;

    public MiddleSearcher(Board board, Searcher searcher, Evaluator evaluator, MoveGenerator moveGenerator) {
        this.board = board;
        this.searcher = searcher;
        this.evaluator = evaluator;
        this.moveGenerator = moveGenerator;
        this.quiescenceSearch = new QuiescenceSearch(board, evaluator, moveGenerator, searcher, transpositionTable);
    }

    @Override
    public void search() {
        UciSender.sendDebugMessage("Entered middle searcher");

        for(int i=1; i<255; i++) {
            evaluator.counter = 0;
            Result result;

            if(Config.ALPHA_BETA_ON)
                result = alphaBetaNegEntryPoint(i+DEPTH_FOR_QUIESCENCE, MINIMUM, MAXIMUM);
            else
                result = negaMaxEntryPoint(i);

            if(result != null) {
                searcher.bestMove = result.move;
                UciSender.sendInfoMessage("depth " + i + " score cp " + evaluator.evaluate() + " pv " + searcher.bestMove + " nodes " + evaluator.getCount());
                if(result.score == MAXIMUM) {
                    return;
                }
            }
            if(!searcher.isCurrentlyThinking)
                return;
        }
    }

    private Result alphaBetaNegEntryPoint(int depth, int alpha, int beta) {

        int bestMoveValue = MINIMUM;
        Move bestMove = null;

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();
        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        for(Move move : moves) {

            if(!searcher.isCurrentlyThinking)
                return null;

            if(board.makeMove(move)){
                int score = -alphaBetaNeg(depth-1, -beta, -alpha);
                if((score > bestMoveValue || bestMoveValue == MINIMUM) && searcher.isCurrentlyThinking)
                {
                    bestMoveValue = score;
                    bestMove = move;
                    if(score > alpha)
                        alpha = score;
                }
                board.unmakeMove();

                if (bestMoveValue == MAXIMUM) {
                    return new Result(bestMoveValue, bestMove);
                }

                if(score >= beta)
                    break;
            }
            else
                board.unmakeMove();
        }

        return new Result(bestMoveValue, bestMove);
    }

    private Integer alphaBetaNeg(int depth, int alpha, int beta) {

        Integer result = transpositionTable.get(board.zobristHashing.getHash(), depth, board.isWhiteToPlay());
        if (result != null) {
            return result;
        }

        if(depth == DEPTH_FOR_QUIESCENCE) {
            if(!Config.QUIESCENCE_SEARCH_ON)
                return evaluator.evaluate();
            else {
                result = quiescenceSearch.search(depth-1, alpha, beta);
                transpositionTable.put(board.zobristHashing.getHash(), depth, result, board.isWhiteToPlay());
                return result;
            }
        }

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();
        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        int bestMoveValue = MINIMUM;
        for(Move move : moves) {
            if(!searcher.isCurrentlyThinking)
                break;

            if(board.makeMove(move)) {
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
            else
                board.unmakeMove();
        }
        transpositionTable.put(board.zobristHashing.getHash(), depth, bestMoveValue, board.isWhiteToPlay());

        return bestMoveValue;
    }



    private Result negaMaxEntryPoint(int depth) {
        int bestMoveValue = MINIMUM;
        Move bestMove = null;

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();

        for(Move move : moves) {

            if(!searcher.isCurrentlyThinking)
                return null;

            if(board.makeMove(move)){
                int score = -negaMax(depth-1);
                if((score > bestMoveValue || bestMoveValue == MINIMUM) && searcher.isCurrentlyThinking)
                {
                    bestMoveValue = score;
                    bestMove = move;
                }
                board.unmakeMove();

                if (bestMoveValue == MAXIMUM)
                    return new Result(bestMoveValue, bestMove);
            }
            else
                board.unmakeMove();
        }

        return new Result(bestMoveValue, bestMove);
    }

    private Integer negaMax(int depth) {

        if(depth == 0) {
            return evaluator.evaluate();
        }

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();

        int bestMoveValue = MINIMUM;
        for(Move move : moves) {
            if(!searcher.isCurrentlyThinking)
                break;

            if(board.makeMove(move)) {
                int score = -negaMax(depth-1);
                if(score > bestMoveValue)
                    bestMoveValue = score;
                board.unmakeMove();
            }
            else
                board.unmakeMove();
        }
        return bestMoveValue;
    }


}

