package org.example.Engine;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move;
import org.example.Engine.MoveGeneration.MoveGenerator;
import org.example.Engine.Search.Searcher;
import org.example.Engine.StateEvaluation.Evaluator;
import org.example.UciSender;

public class Engine {
    public final String NAME = "KobayashiMaru";
    public final String AUTHOR = "Krzysztof Antoni Wiśniewski";

    private final Board board = new Board();
    private final MoveGenerator moveGenerator = new MoveGenerator();
    private final Searcher searcher = new Searcher();
    private final Evaluator evaluator = new Evaluator();

    public boolean debugOn = false;

    public void initiateDefaultPosition() {
        board.startFromDefaultPosition();
    }

    public void initiateCustomPosition(String fen) {
        board.startFromCustomPosition(fen);
    }

    public void makeBestMove() {
        Move bestMove = searcher.search(board, evaluator);

        board.makeDefiniteMove();
        UciSender.sendEngineResponse(bestMove.toString());
    }
}
