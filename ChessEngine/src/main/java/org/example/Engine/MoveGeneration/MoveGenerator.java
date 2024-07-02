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
    Generator kingMoveGenerator;
    Generator slidingMoveGenerator;

    public AttackOfKingGenerator checkGenerator;

    public MoveGenerator(Board board)
    {
        this.board = board;
        this.checkGenerator = new AttackOfKingGenerator(board, this);

        pawnMoveGenerator = new PawnMoveGenerator(board);
        knightMoveGenerator = new KnightMoveGenerator(board);
        kingMoveGenerator = new KingMoveGenerator(board, checkGenerator);
        slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public ArrayList<Move> generateMoves() {
        ArrayList<Move> moves = new ArrayList<>(40);

        boolean whiteToMove = board.currentBoardState.whiteToMove;
        short color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.getSpecificPiecesBitBoard(WHITE) : board.getSpecificPiecesBitBoard(BLACK);
        long opponentPieces = whiteToMove ? board.getSpecificPiecesBitBoard(BLACK) : board.getSpecificPiecesBitBoard(WHITE);
        long emptySquares = ~(myPieces | opponentPieces);

        moves.addAll(kingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(pawnMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(knightMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(slidingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));

        return deleteMovesThatPutKingInCheck(moves, color);
    }

    private ArrayList<Move> deleteMovesThatPutKingInCheck(ArrayList<Move> moves, short color) {
       ArrayList<Move> legalMoves = new ArrayList<>(40);

        for(Move move : moves) {
            board.makeMove(move);
            if(!checkGenerator.isKingInCheck(color))
                legalMoves.add(move);
            board.unmakeMove();
        }

        return legalMoves;
    }
}
