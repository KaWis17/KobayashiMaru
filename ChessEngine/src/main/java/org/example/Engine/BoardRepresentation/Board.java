package org.example.Engine.BoardRepresentation;

public class Board {

    public BoardConstants.GAME_STATE state = BoardConstants.GAME_STATE.EARLY_GAME;

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardConstants.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        //TODO implement
    }
}
