package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public abstract class Generator {

    ArrayList<Move> possibleMoves = new ArrayList<>();
    abstract ArrayList<Move> generateMoves(Board board);

    void addMovesFromMask(long mask) {
        //Move move = new Move();
    }
}
