package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;
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
    TranspositionTable transpositionTable;

    QuiescenceSearch(Board board, Evaluator evaluator, MoveGenerator generator, Searcher searcher, TranspositionTable transpositionTable) {
        this.board = board;
        this.evaluator = evaluator;
        this.generator = generator;
        this.searcher = searcher;
        this.transpositionTable = transpositionTable;
    }

    public Integer search(int depth, int alpha, int beta) {
        Integer transpositionValue = transpositionTable.get(board.zobristHashing.getHash(), depth, board.isWhiteToPlay());
        if(transpositionValue != null)
            return transpositionValue;

        int standPat = evaluator.evaluate();

        if(depth == 0) {
            transpositionTable.put(board.zobristHashing.getHash(), depth, standPat, board.isWhiteToPlay());
            return standPat;
        }

        if (standPat >= beta) {
            transpositionTable.put(board.zobristHashing.getHash(), depth, beta, board.isWhiteToPlay());
            return beta;
        }
        if (alpha < standPat)
            alpha = standPat;

        ArrayList<Move> moves = generator.generateAllPseudoLegalCaptureMoves();
        if(Config.STATIC_MOVE_ORDERING_ON)
            Collections.sort(moves);

        for(Move move : moves) {

            if(!searcher.isCurrentlyThinking)
                break;

            if(board.makeMove(move)) {
                int score = -search(depth-1, -beta, -alpha);
                board.unmakeMove();

                if(score >= beta) {
                    transpositionTable.put(board.zobristHashing.getHash(), depth, beta, board.isWhiteToPlay());
                    return beta;
                }
                if(score > alpha)
                    alpha = score;
            }
            else
                board.unmakeMove();
        }

        transpositionTable.put(board.zobristHashing.getHash(), depth, alpha, board.isWhiteToPlay());
        return alpha;
    }
}
