package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Search;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.*;


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
        this.quiescenceSearch = new QuiescenceSearch(board, evaluator, moveGenerator, searcher, transpositionTable, this);
    }

    public void newGame () {
        transpositionTable = new TranspositionTable();
    }

    int currentDepthSearchStart;
    @Override
    public void search() {
        UciSender.sendDebugMessage("Entered middle searcher");
        int estimationDelta = 30;
        int prevBestEval = -100_000_000;

//        if(Config.TRANSPOSITION_TABLE_ON)
//            transpositionTable.clear();

        for(int i=1; i<255; i++) {

            Time time = new Time(System.currentTimeMillis());
            evaluator.counter = 0;
            Result result;
            if(Config.ALPHA_BETA_ON) {
                currentDepthSearchStart = i + DEPTH_FOR_QUIESCENCE;
                if (Config.ESTIMATION_WINDOW_ON && i > 2) {
                    result = alphaBetaNegEntryPoint(currentDepthSearchStart, prevBestEval - estimationDelta, prevBestEval + estimationDelta);
                } else
                    result = alphaBetaNegEntryPoint(currentDepthSearchStart, MINIMUM, MAXIMUM);
            }
            else {
                currentDepthSearchStart = i;
                result = negaMaxEntryPoint(currentDepthSearchStart);
            }

            if(result != null) {
                if(Config.ESTIMATION_WINDOW_ON && i > 2) {
                    if ((result.score <= prevBestEval - estimationDelta) || (result.score >= prevBestEval + estimationDelta)) {
                        System.out.println("\n\n\nEstimation window miss with result " + result.score + " and prevBestEval " + prevBestEval +"\n\n\n");
                        result = alphaBetaNegEntryPoint(i + DEPTH_FOR_QUIESCENCE, MINIMUM, MAXIMUM);
                        System.out.println("New result " + result.score + " " + result.move);
                    }
                }
                if(result != null) {
                    searcher.bestMove = result.move;
                    prevBestEval = result.score;
                    UciSender.sendInfoMessage("depth " + i + " score cp " + result.score + " pv " + searcher.bestMove + " nodes " + evaluator.getCount() + " time " + (System.currentTimeMillis() - time.getTime()) + " transposition: " + transpositionTable.cache.size());
                    if(result.score == MAXIMUM) {
                        return;
                    }
                }
            }

            if(!searcher.isCurrentlyThinking)
                return;
        }
    }

    private Result alphaBetaNegEntryPoint(int depth, int alpha, int beta) {

        int alphaOrigin = alpha;

        int bestMoveValue = MINIMUM;
        Move bestMove = null;

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();
        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        for(Move move : moves) {

            if(!searcher.isCurrentlyThinking)
                return null;

            if(board.makeMove(move)){
                int score = -alphaBetaNeg(depth-1, -beta, -alpha, false);
                if((score > bestMoveValue || bestMoveValue == MINIMUM) && searcher.isCurrentlyThinking)
                {
                    bestMoveValue = score;
                    bestMove = move;
                    if(score > alpha)
                        alpha = score;
                }
                board.unmakeMove();

                if (bestMoveValue == MAXIMUM) {
                    break;
                }

                if(alpha >= beta)
                    break;
            }
            else
                board.unmakeMove();
        }

        if(searcher.isCurrentlyThinking) {
            if(bestMoveValue <= alphaOrigin)
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.UPPER_BOUND);
            else if(bestMoveValue >= beta)
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.LOWER_BOUND);
            else
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.EXACT);
        }

        return new Result(bestMoveValue, bestMove);
    }

    private Integer alphaBetaNeg(int depth, int alpha, int beta, boolean wasExtended) {
        int alphaOrigin = alpha;

        TranspositionResult result = transpositionTable.get(board.zobristHashing.getTranspositionHash(), depth);
        if (result != null) {
            if (result.flag == TranspositionResult.Flag.EXACT) return result.score;
            else if (result.flag == TranspositionResult.Flag.LOWER_BOUND) alpha = max(alpha, result.score);
            else if (result.flag == TranspositionResult.Flag.UPPER_BOUND) beta = min(beta, result.score);

            if (alpha >= beta) return result.score;
        }

        if(depth == DEPTH_FOR_QUIESCENCE) {
            if(!Config.QUIESCENCE_SEARCH_ON) {
                return evaluator.evaluate(currentDepthSearchStart - depth);
            }
            else {
                return quiescenceSearch.search(depth-1, alpha, beta);
            }
        }

        ArrayList<Move> moves = moveGenerator.generateAllPseudoLegalMoves();

        if(moves.isEmpty())
            return evaluator.evaluate(currentDepthSearchStart - depth);

        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        int bestMoveValue = MINIMUM;
        for(Move move : moves) {
            if(!searcher.isCurrentlyThinking)
                break;

            if(board.makeMove(move)) {
                if(Config.MOVE_EXTENSIONS_ON && !wasExtended) {
                    if(moves.size() == 1 || board.isBlackInCheck() || board.isWhiteInCheck())
                        bestMoveValue = max(bestMoveValue, -alphaBetaNeg(depth, -beta, -alpha, true));

                    else
                        bestMoveValue = max(bestMoveValue, -alphaBetaNeg(depth-1, -beta, -alpha, false));
                }
                else {
                    bestMoveValue = max(bestMoveValue, -alphaBetaNeg(depth-1, -beta, -alpha, false));
                }
                alpha = max(alpha, bestMoveValue);
                board.unmakeMove();
                if(alpha >= beta)
                    break;
            }
            else
                board.unmakeMove();
        }

        if(searcher.isCurrentlyThinking) {
            if(bestMoveValue <= alphaOrigin)
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.UPPER_BOUND);
            else if(bestMoveValue >= beta)
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.LOWER_BOUND);
            else
                transpositionTable.put(board.zobristHashing.getTranspositionHash(), depth, bestMoveValue, TranspositionResult.Flag.EXACT);
        }

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
            return evaluator.evaluate(currentDepthSearchStart - depth);
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

