package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.FenImplementer;
import org.example.Engine.BoardRepresentation.Move;
import org.example.Engine.StateEvaluation.Evaluator;

import java.util.HashMap;
import java.util.Random;

public class Searcher {

    HashMap<String, String[]> map = new HashMap<>();
    Random random = new Random();
    Board board;
    Evaluator evaluator;

    public Searcher(Board board, Evaluator evaluator) {
        this.board = board;
        this.evaluator = evaluator;
    }

    public Move search() {

        if(board.state == BoardConstants.GAME_STATE.EARLY_GAME)
            return findMoveForEarlyGame();

        else if(board.state == BoardConstants.GAME_STATE.END_GAME)
            return findMoveForEndGame();

        return findMoveForMidGame();

    }

    private Move findMoveForEarlyGame() {
        String currentFen = FenImplementer.generateFEN(board);
        String[] moves = map.get(currentFen);

        if(moves == null) {
            board.state = BoardConstants.GAME_STATE.MID_GAME;
            return findMoveForMidGame();
        }

        String selectedString = moves[random.nextInt(moves.length)];

        return new Move(selectedString);
    }

    private Move findMoveForMidGame() {

        if(board.shouldSwitchToEndgame()){
            board.state = BoardConstants.GAME_STATE.END_GAME;
            return findMoveForEndGame();
        }

        return null;
    }

    private Move findMoveForEndGame() {
        return null;
    }

}
