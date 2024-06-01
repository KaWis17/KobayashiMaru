package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move;
import org.example.Engine.StateEvaluation.Evaluator;

public class Searcher {

    public Move search(Board board, Evaluator evaluator) {

        if(board.state == BoardConstants.GAME_STATE.EARLY_GAME)
            return findMoveForEarlyGame("../Resources/OpeningLibrary");

        else if(board.state == BoardConstants.GAME_STATE.MID_GAME)
            return findMoveForMidGame(evaluator);

        return findMoveForEndGame("../Resources/EndgameTablebases");

    }

    private Move findMoveForEarlyGame(String path) {
        return null;
    }

    private Move findMoveForMidGame(Evaluator evaluator) {
        return null;
    }

    private Move findMoveForEndGame(String path) {
        return null;
    }

}
