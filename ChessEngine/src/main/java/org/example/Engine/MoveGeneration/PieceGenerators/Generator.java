package org.example.Engine.MoveGeneration.PieceGenerators;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveConstants;
import org.example.Engine.MoveGeneration.Constants;

import java.util.ArrayList;

public abstract class Generator implements BoardConstants, Constants, MoveConstants {

    Board board;
    long allOpponentColor;
    long allMyColor;
    long allEmpty;

    ArrayList<Move> possibleMoves = new ArrayList<>(64);
    public abstract ArrayList<Move> generateMoves(short myColor, long allMyColor, long allOpponentColor, long allEmpty);

    Generator(Board board) {
        this.board = board;
    }

}
