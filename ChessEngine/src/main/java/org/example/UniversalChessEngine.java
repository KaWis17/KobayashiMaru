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
            case "isready" -> sendEngineResponse("readyok");
            case "setoption" -> processSetOptionCommand(command);
            case "register" -> processRegisterCommand(command);
            case "ucinewgame" -> processNewGameCommand(command);
            case "position" -> processPositionCommand(command);
            case "go" -> processGoCommand(command);
            case "stop" -> processStop(command);
            case "ponderhit" -> processPonderHit(command);
            // case "quit" -> has been handled by App.java
            default -> throw new UniversalChessEngine.UnknownCommandException();
        }
    }

    private void processUciCommand() {
        sendEngineResponse("id name " + engine.NAME);
        sendEngineResponse("id author " + engine.AUTHOR);
        sendEngineResponse("uciok");
    }

    private void processDebugCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processSetOptionCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processRegisterCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processNewGameCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processPositionCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processGoCommand(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processStop(String command) {
        sendEngineResponse(command + " ok");
    }

    private void processPonderHit(String command) {
        sendEngineResponse(command + " ok");
    }

    private void sendEngineResponse(String response) {
        System.out.println(response);
    }

    private static class UnknownCommandException extends RuntimeException {
        UnknownCommandException() {
            super("Unknown command has been entered to UCI!");
        }
    }
}
