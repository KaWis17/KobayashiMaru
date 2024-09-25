package org.example.Engine.Search;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.EarlySearcher.EarlySearcher;
import org.example.Engine.Search.MiddleSearcher.MiddleSearcher;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

public class Searcher implements Runnable {

    EarlySearcher earlySearcher;
    MiddleSearcher middleSearcher;

    Board board;
    Evaluator evaluator;

    public int searchId;
    public volatile Move bestMove;
    public volatile boolean stopSearch;

    public Searcher(Board board, Evaluator evaluator, MoveGenerator moveGenerator) {
        this.board = board;
        this.evaluator = evaluator;
        searchId = 0;

        earlySearcher = new EarlySearcher(board, this);
        middleSearcher = new MiddleSearcher(board, this, evaluator, moveGenerator);

    }

    @Override
    public void run() {
        stopSearch = false;
        searchId++;

        long time = System.currentTimeMillis();
        if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Searching, searchId = " + searchId);

        search();

        if(Config.DEBUG_ON){
            UciSender.sendDebugMessage("Finished search, searchId = " + searchId);
            UciSender.sendDebugMessage("Time taken: " + (System.currentTimeMillis() - time) + "ms");
        }

        if(!stopSearch)
            stopSearch=true;
    }

    public void stopSearchAndSendResponse(){
        stopSearch = true;
        UciSender.sendBestMove(bestMove.toString());
    }

    public void search() {
        if(Config.OPENING_LIBRARY_ON)
            earlySearcher.search();
        else
            middleSearcher.search();
    }
}
