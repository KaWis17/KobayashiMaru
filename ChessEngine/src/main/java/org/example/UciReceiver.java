package org.example;

import org.example.Engine.Engine;

public class UciReceiver {

    Engine engine;

    UciReceiver() {
        engine = new Engine();
    }

    public void dispatch(String command) {

        command = command.toLowerCase();
        String[] splittedCommand = command.split(" ");

        switch(splittedCommand[0]) {
            case "uci" -> processUciCommand();
            case "debug" -> processDebugCommand(command);
            case "isready" -> processIsReadyCommand();
            case "setoption" -> processSetOptionCommand(command);
            case "register" -> processRegisterCommand(command);
            case "ucinewgame" -> processNewGameCommand();
            case "position" -> processPositionCommand(command);
            case "go" -> processGoCommand(command);
            case "stop" -> processStopCommand(command);
            case "ponderhit" -> processPonderHitCommand(command);
            case "display" -> processDisplayCommand();
            default -> throw new UciReceiver.UnknownCommandException();
        }
    }

    private void processUciCommand() {
        UciSender.sendEngineInformation(engine);
        UciSender.sendOkInformation();
    }

    private void processDebugCommand(String command) {
        String[] commandSplit = command.split(" ");
        /*
        if(commandSplit[1].equals("on"))
            engine.debugOn = true;
        else if(commandSplit[1].equals("off"))
            engine.debugOn = false;
        else
            throw new UnknownCommandException();
            
         */
    }

    private void processIsReadyCommand(){
        UciSender.sendReadyOk();
    }

    private void processSetOptionCommand(String command) {
       UciSender.sendUnsupportedCommand();
    }

    private void processRegisterCommand(String command) {
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

    private void handleStartingPosition(String command) {
        if(command.contains("startpos"))
            engine.initiateDefaultPosition();
        else if(command.contains("fen"))
            engine.initiateCustomPosition(command.split(" ",3)[2]);
        else
            throw new UnknownPositionCommand();
    }

    private void handleMovesAfterPosition(String command) {
        String moves = command.split("moves ")[1];
        String[] splittedMoves = moves.split(" ");

        for(String move : splittedMoves)
            engine.makeMove(move);
    }

    private void processGoCommand(String command) {
        if(command.contains("movetime"))
            processGoMoveTimeCommand(command);
        else
            processGoWithoutTimeCommand(command);
    }

    private void processGoMoveTimeCommand(String command) {
        int indexOfMovetime = command.indexOf("movetime");
        String[] splittedCommand = command.substring(indexOfMovetime+9).split(" ");
        int time = Integer.parseInt(splittedCommand[0]);

        engine.findBestMoveWithSpecificTime(time);
    }

    private void processGoWithoutTimeCommand(String command) {
        engine.findBestMoveWithTimeProposal();
    }

    private void processStopCommand(String command) {
        engine.stopSearchingForBestMoveManually();
    }

    private void processPonderHitCommand(String command) {
        UciSender.sendUnsupportedCommand();
    }

    private void processDisplayCommand() {
        engine.displayBoard();
    }

    private static class UnknownCommandException extends RuntimeException {
        UnknownCommandException() {
            super("Unknown command has been entered to UCI!");
        }
    }

    private static class UnknownPositionCommand extends RuntimeException {
        UnknownPositionCommand() {
            super("Unknown position command!");
        }
    }
}
