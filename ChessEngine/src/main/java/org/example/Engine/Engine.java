package org.example.Engine;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.Search.Searcher;
import org.example.UciSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Engine {
    public final String NAME = "KobayashiMaru";
    public final String AUTHOR = "Krzysztof Antoni Wiśniewski";

    private final Board board = new Board();
    private final Searcher searcher = new Searcher(this, board);

    private Thread searcherThread;
    ScheduledExecutorService executorService;

    public void initiateDefaultPosition() {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("initiatingDefaultPosition");

        board.startFromDefaultPosition();
    }

    public void initiateCustomPosition(String fen) {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("initiatingPosition");

        board.startFromCustomPosition(fen);
    }

    public void makeMove(String move) {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("makingMove: " + move);

        board.makeMove(new Move(move, board));
    }

    public void findBestMoveWithTimeProposal() {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("makingBestMoveWithTimeProposal");

        long time = getTimeProposal();
        findBestMoveWithSpecificTime(time);
    }

    public void findBestMoveWithSpecificTime(long time) {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("startingSearchOnNewThread, time = " + time);

        searcherThread = new Thread(searcher);
        searcherThread.start();

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::stopSearchingForBestMove, time, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public void stopSearchingForBestMoveManually() {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("stoppingSearchManually");

        executorService.shutdownNow();

        stopSearchingForBestMove();
    }

    private void stopSearchingForBestMove() {
        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("stoppingSearch");

        searcherThread.interrupt();

        UciSender.sendBestMove(searcher.bestMove.toString());
        board.makeMove(searcher.bestMove);
    }

    public void displayBoard() {
        System.out.println(board);
    }

    private long getTimeProposal() {
        long proposal = 1000;

        if(Args.DEBUG_ON)
            UciSender.sendDebugMessage("timeProposal: " + proposal);

        return proposal;
    }
}
