package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class MoveGenerator {

    KingMoveGenerator kg;
    {
        kg = new KingMoveGenerator();
    }
    public ArrayList<Move> generateMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>(218);
        moves.addAll(kg.generateMoves(board));
        moves.addAll(KnightMoveGenerator.generateMoves(board));
        moves.addAll(SlidingMoveGenerator.generateMoves(board));
        moves.addAll(PawnMoveGenerator.generateMoves(board));

        return moves;
    }

}
