package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.PieceGenerators.*;

import java.util.ArrayList;

public class MoveGenerator implements BoardConstants {

    Board board;
    Generator pawnMoveGenerator;
    Generator knightMoveGenerator;

    MoveGenerator(Board board)
    {
        this.board = board;
        pawnMoveGenerator = new PawnMoveGenerator(board);
        knightMoveGenerator = new KnightMoveGenerator(board);
        Generator kingMoveGenerator = new KingMoveGenerator(board);
        Generator slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public ArrayList<Move> generateMoves() {
        ArrayList<Move> moves = new ArrayList<>(40);

        boolean whiteToMove = board.currentBoardState.whiteToMove;
        short color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.bitBoardsRepresentation.bitBoards[WHITE] : board.bitBoardsRepresentation.bitBoards[BLACK];
        long opponentPieces = whiteToMove ? board.bitBoardsRepresentation.bitBoards[BLACK] : board.bitBoardsRepresentation.bitBoards[WHITE];
        long emptySquares = ~(myPieces | opponentPieces);

        //moves.addAll(pawnMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(knightMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        return moves;
    }

}
