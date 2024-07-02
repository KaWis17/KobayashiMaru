package org.example.Engine.Search;

import org.example.Engine.Args;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.Engine;
import org.example.Engine.StateEvaluation.Evaluation;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

import static org.example.Engine.BoardRepresentation.BoardConstants.GAME_STATE.EARLY_GAME;
import static org.example.Engine.BoardRepresentation.BoardConstants.GAME_STATE.MID_GAME;

public class Searcher implements Runnable {

    EarlySearcher earlySearcher;
    MiddleSearcher middleSearcher;
    EndSearcher endSearcher;

    Board board;
    Engine engine;
    Evaluator evaluator;

    public volatile Move bestMove;
    public int searchId;
    public volatile boolean stopSearch;

    public Searcher(Engine engine, Board board, Evaluator evaluator) {
        this.board = board;
        this.engine = engine;
        this.evaluator = evaluator;
        searchId = 0;

        earlySearcher = new EarlySearcher(board);
        middleSearcher = new MiddleSearcher(board, this, evaluator);
        endSearcher = new EndSearcher(board);
    }

    @Override
    public void run() {
        stopSearch = false;
        searchId++;

        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("searching, searchId = " + searchId);

        search();

        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("search finished, searchId = " + searchId);


        if(!stopSearch)
            engine.stopSearchingForBestMoveManually();
    }

    public void search() {

        middleSearcher.search();

    }
}
