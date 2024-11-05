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

    public static void sendOptionInformation(Engine engine) {
        System.out.println("option name Debug type check default false");
        System.out.println("option name OwnBook type check default true");
        System.out.println("option name AlphaBeta type check default true");
        System.out.println("option name Quiescence type check default true");
        System.out.println("option name StaticMoveOrdering type check default true");
        System.out.println("option name ZobristHashing type check default true");
        System.out.println("option name TranspositionTable type check default true");
        System.out.println("option name EstimationWindow type check default true");
        System.out.println("option name MoveExtensions type check default true");
        System.out.println("option name PieceSquareTable type check default true");
        System.out.println("option name KingSafety type check default true");
        System.out.println("option name PawnStructure type check default true");
        System.out.println("option name Mobility type check default true");
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
        System.out.println(dtf.format(LocalDateTime.now()) + " INFO: " + message);
    }

    public static void sendInfoMessage(String message) {
        System.out.println("info " + message);
    }

}
