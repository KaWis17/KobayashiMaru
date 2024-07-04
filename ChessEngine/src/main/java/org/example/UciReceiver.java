package org.example;

import org.example.Engine.Args.Config;
import org.example.Engine.Engine;

public class UciReceiver {

    Engine engine;

    UciReceiver() {
        engine = new Engine();
    }

    public void dispatch(String command) {

        String[] splittedCommand = command.split(" ");

        switch(splittedCommand[0]) {
            case "uci" -> processUciCommand();
            case "debug" -> processDebugCommand(command);
            case "isready" -> processIsReadyCommand();
            case "setoption" -> processSetOptionCommand();
            case "register" -> processRegisterCommand();
            case "ucinewgame" -> processNewGameCommand();
            case "position" -> processPositionCommand(command);
            case "go" -> processGoCommand(command);
            case "stop" -> processStopCommand();
            case "ponderhit" -> processPonderHitCommand();
            case "display", "d" -> processDisplayCommand();
            case "perft" -> processPerftCommand(command);
        }
    }

    private void processUciCommand() {
        UciSender.sendEngineInformation(engine);
        UciSender.sendOptionInformation(engine);
        UciSender.sendOkInformation();
    }

    private void processDebugCommand(String command) {
        String[] commandSplit = command.split(" ");

        if(commandSplit[1].equals("on"))
            Config.DEBUG_ON = true;
        else if(commandSplit[1].equals("off"))
            Config.DEBUG_ON = false;
    }

    private void processIsReadyCommand(){
        UciSender.sendReadyOk();
    }

    // TODO: Implement this method
    private void processSetOptionCommand() {
       UciSender.sendUnsupportedCommand();
    }

    // TODO: Implement this method
    private void processRegisterCommand() {
        UciSender.sendUnsupportedCommand();
    }

    private void processNewGameCommand() {
        engine = new Engine();
    }

    private void processPositionCommand(String command) {
        handleStartingPosition(command);
        if(command.contains("moves"))
            handleMovesAfterPosition(command);
    }

    private void processGoCommand(String command) {
        // TODO: Implement this method
        if(command.contains("ponder"))
            UciSender.sendUnsupportedCommand();

        else if(command.contains("movetime"))
            processGoWithTimeOrder(command);

        else if(command.contains("wtime") && command.contains("btime") &&
                command.contains("winc") && command.contains("binc"))
            processGoWithTimeEstimate(command);
        
        else if(command.contains("infinite"))
            processGoInfinite();
    }

    private void processStopCommand() {
        engine.stopSearchManually();
    }

    // TODO: Implement this method
    private void processPonderHitCommand() {
        UciSender.sendUnsupportedCommand();
    }

    private void processDisplayCommand() {
        engine.displayBoard();
    }

    private void processPerftCommand(String command) {
        int depth = Integer.parseInt(command.split(" ")[1]);
        engine.perft(depth);
    }

    private void handleStartingPosition(String command) {
        if(command.contains("startpos"))
            engine.initiateDefaultPosition();
        else if(command.contains("fen"))
            engine.initiateCustomPosition(command.split(" ",3)[2]);
    }

    private void handleMovesAfterPosition(String command) {
        String moves = command.split("moves ")[1];
        String[] splittedMoves = moves.split(" ");

        for(String move : splittedMoves)
            engine.makeMove(move);
    }

    private void processGoWithTimeOrder(String command) {
        int time = Integer.parseInt(command.split("movetime ")[1].split(" ")[0]);
        engine.findBestMoveWithTimeOnThread(time);
    }

    private void processGoWithTimeEstimate(String command) {
        int wtime = Integer.parseInt(command.split("wtime ")[1].split(" ")[0]);
        int btime = Integer.parseInt(command.split("btime ")[1].split(" ")[0]);
        int winc = Integer.parseInt(command.split("winc ")[1].split(" ")[0]);
        int binc = Integer.parseInt(command.split("binc ")[1].split(" ")[0]);

        engine.findBestMoveWithTimeProposal(wtime, btime, winc, binc);
    }

    private void processGoInfinite(){
        engine.findBestMoveWithoutTimeLimit();
    }
}
