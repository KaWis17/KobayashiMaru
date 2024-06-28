package org.example.Engine.Search;

import org.example.Engine.Args;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.Engine;
import org.example.UciSender;

import static org.example.Engine.BoardRepresentation.BoardConstants.GAME_STATE.EARLY_GAME;
import static org.example.Engine.BoardRepresentation.BoardConstants.GAME_STATE.MID_GAME;

public class Searcher implements Runnable {

    EarlySearcher earlySearcher;
    MiddleSearcher middleSearcher;
    EndSearcher endSearcher;

    Board board;
    Engine engine;

    public volatile Move bestMove;
    public int searchId;

    public Searcher(Engine engine, Board board) {
        this.board = board;
        this.engine = engine;
        searchId = 0;

        earlySearcher = new EarlySearcher(board);
        middleSearcher = new MiddleSearcher(board);
        endSearcher = new EndSearcher(board);
    }

    @Override
    public void run() {
        searchId++;

        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("searching, searchId = " + searchId);

        search();

        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("search finished, searchId = " + searchId);

        engine.stopSearchingForBestMoveManually();
    }

    public void search() {

        if(Args.USE_OPENING_BOOK && board.currentBoardState.gameState == EARLY_GAME) {
            bestMove = earlySearcher.search();
            if(bestMove == null) {
                board.currentBoardState.gameState = MID_GAME;
                search();
            }
        }
        else
            bestMove = middleSearcher.search();

    }
}
