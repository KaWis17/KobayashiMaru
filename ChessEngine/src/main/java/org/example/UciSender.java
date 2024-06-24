package org.example;

import org.example.Engine.Engine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UciSender {

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void sendEngineInformation(Engine engine) {
        System.out.println("id name " + engine.NAME);
        System.out.println("id author " + engine.AUTHOR);
    }

    public static void sendOkInformation() {
        System.out.println("uciok");
    }

    public static void sendReadyOk() {
        System.out.println("readyok");
    }

    public static void sendUnsupportedCommand() {
        System.out.println("Unsupported command");
    }

    public static void sendBestMove(String response) {
        System.out.println("bestmove " + response);
    }

    public static void sendDebugMessage(String message) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now) + " INFO: " + message);
    }

}
