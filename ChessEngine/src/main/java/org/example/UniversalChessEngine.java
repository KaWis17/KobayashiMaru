package org.example;

import org.example.Engine.Engine;

public class UniversalChessEngine {

    Engine engine;

    UniversalChessEngine() {
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
            // case "quit" -> has been handled by App.java
            case "display" -> processDisplayCommand();
            default -> throw new UniversalChessEngine.UnknownCommandException();
        }
    }

    private void processUciCommand() {
        sendEngineResponse("id name " + engine.NAME);
        sendEngineResponse("id author " + engine.AUTHOR);
        sendEngineResponse("uciok");
    }

    private void processDebugCommand(String command) {
        String[] commandSplit = command.split(" ");

        if(commandSplit[1].equals("on"))
            engine.debugOn = true;
        else if(commandSplit[1].equals("off"))
            engine.debugOn = false;
        else
            throw new UnknownCommandException();
    }

    private void processIsReadyCommand(){
        sendEngineResponse("readyok");
    }

    private void processSetOptionCommand(String command) {
        //TODO currently unsupported
    }

    private void processRegisterCommand(String command) {
        //TODO currently unsupported
    }

    private void processNewGameCommand() {
        engine.prepareNewGame();
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
            engine.initiateCustomPosition(command.split(" ")[3]);
        else
            throw new UnknownPositionCommand();
    }

    private void handleMovesAfterPosition(String command) {
        //TODO currently unsupported
    }

    private void processGoCommand(String command) {
        //TODO currently unsupported
    }

    private void processStopCommand(String command) {
        //TODO currently unsupported
    }

    private void processPonderHitCommand(String command) {
        //TODO currently unsupported
    }

    private void processDisplayCommand() {
        //TODO currently unsupported
    }

    private void sendEngineResponse(String response) {
        System.out.println(response);
    }

    private static class UnknownCommandException extends RuntimeException {
        UnknownCommandException() {
            super("Unknown command has been entered to UCI!");
        }
    }

    private static class UnknownPositionCommand extends RuntimeException {
        UnknownPositionCommand() {
            super("Unknown FEN position!");
        }
    }
}
