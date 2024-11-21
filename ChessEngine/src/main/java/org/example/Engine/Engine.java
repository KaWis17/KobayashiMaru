package org.example.Engine;

import org.example.Engine.Args.Config;
import org.example.Engine.Args.Constants;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.MoveGeneration.PerftTest;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Engine implements Constants {

    private final Board board = new Board();
    private final MoveGenerator moveGenerator = new MoveGenerator(board);
    private final Evaluator evaluator = new Evaluator(board);
    private final Searcher searcher = new Searcher(board, evaluator, moveGenerator);

    private ScheduledExecutorService executorService;

    // LOADING POSITIONS
    public void initiateDefaultPosition() {
        if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Initiating default position");

        board.startFromDefaultPosition();
    }

    public void initiateCustomPosition(String fen) {
        if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Initiating custom position");

        board.startFromCustomPosition(fen);
    }

    public void makeMove(String move) {
        board.makeMove(move);
    }

    // SEARCHING
    public void findBestMoveWithTimeProposal(int wtime, int btime, int winc, int binc) {
        int timeDecidedForMove = decideTimeProposal(wtime, btime, winc, binc);

        if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Making best move with time proposal: " + timeDecidedForMove);
        findBestMoveWithTimeOnThread(timeDecidedForMove);
    }

    public void findBestMoveWithoutTimeLimit() {
        if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Making best move without time limit");
        findBestMoveWithTimeOnThread(Integer.MAX_VALUE);
    }

    public void findBestMoveWithTimeOnThread(int time) {
        if(invalidConfig()){
            UciSender.sendInfoMessage("INVALID CONFIG");
            return;
        }

        if(!searcher.isCurrentlyThinking) {
            if(Config.DEBUG_ON)
                UciSender.sendDebugMessage("Starting search on separate thread");

            new Thread(searcher).start();
            scheduleExecutorService(time-100);
        }
        else if(Config.DEBUG_ON)
            UciSender.sendDebugMessage("Engine is currently calculating position, cannot run in paralel.");
    }

    private void scheduleExecutorService(int time) {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::stopSearchingForBestMove, time, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private void stopSearchingForBestMove() {
        if(searcher.isCurrentlyThinking)
            searcher.stopSearchAndSendResponse();
    }

    public void stopSearchManually() {
        executorService.shutdownNow();
        stopSearchingForBestMove();
    }

    // HELPERS
    public void displayBoard() {
        System.out.println(board);
//        int evaluation = evaluator.evaluate();
        int multiply = (board.isWhiteToPlay()) ? 1 : -1;
//        System.out.println("EVAL FOR WHITE: " + multiply * evaluation);
//        System.out.println("EVAL FOR BLACK: " + multiply * -evaluation);
    }

    public void newGame() {
        searcher.newGame();
        board.newGame();
    }

    public void perft(int depth) {
        PerftTest.perft(board, depth);
    }

    public int decideTimeProposal(int wtime, int btime, int winc, int binc) {
        int estimatedMovesLeft = Math.max(40 - board.getCurrentMoveNumber(), 10);
        int estimatedTimeLeft =
                (board.isWhiteToPlay()) ?
                (wtime + winc * estimatedMovesLeft) :
                (btime + binc * estimatedMovesLeft);

        if(estimatedMovesLeft == 10)
            estimatedTimeLeft -= 1000;

        return (estimatedTimeLeft/estimatedMovesLeft - 100);
    }

    public boolean invalidConfig(){
        if(!Config.ALPHA_BETA_ON) {
            if(Config.STATIC_MOVE_ORDERING_ON) {
                UciSender.sendDebugMessage("ENABLED MOVE ORDERING WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
            if(Config.QUIESCENCE_SEARCH_ON){
                UciSender.sendDebugMessage("ENABLED QUIESCENCE SEARCH WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
            if(Config.ZOBRIST_HASHING_ON) {
                UciSender.sendDebugMessage("ENABLED ZOBRIST HASHING WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
            if(Config.TRANSPOSITION_TABLE_ON) {
                UciSender.sendDebugMessage("ENABLED TRANSPOSITION TABLE WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
            if(Config.ESTIMATION_WINDOW_ON) {
                UciSender.sendDebugMessage("ENABLED ESTIMATION WINDOW WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
            if(Config.MOVE_EXTENSIONS_ON) {
                UciSender.sendDebugMessage("ENABLED MOVE EXTENSIONS WITH DISABLED ALPHA BETA IS NOT ALLOWED");
                return true;
            }
        }

        if(Config.TRANSPOSITION_TABLE_ON && !Config.ZOBRIST_HASHING_ON) {
            UciSender.sendDebugMessage("ENABLED TRANSPOSITION TABLE WITH DISABLED ZOBRIST HASHING IS NOT ALLOWED");
            return true;
        }
        return false;
    }
}
