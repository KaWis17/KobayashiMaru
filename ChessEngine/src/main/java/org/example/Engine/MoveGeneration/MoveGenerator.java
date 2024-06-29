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

    public MoveGenerator(Board board)
    {
        this.board = board;

        // pseudo-legal
        pawnMoveGenerator = new PawnMoveGenerator(board);

        // pseudo-legal
        knightMoveGenerator = new KnightMoveGenerator(board);

        // pseudo-legal
        kingMoveGenerator = new KingMoveGenerator(board);

        // pseudo-legal
        slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public ArrayList<Move> generateMoves() {
        ArrayList<Move> moves = new ArrayList<>(40);

        boolean whiteToMove = board.currentBoardState.whiteToMove;
        short color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.getSpecificPiecesBitBoard(WHITE) : board.getSpecificPiecesBitBoard(BLACK);
        long opponentPieces = whiteToMove ? board.getSpecificPiecesBitBoard(BLACK) : board.getSpecificPiecesBitBoard(WHITE);
        long emptySquares = ~(myPieces | opponentPieces);

        moves.addAll(pawnMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(knightMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(kingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(slidingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));

        return deleteMovesThatPutKingInCheck(moves);
    }

    private ArrayList<Move> deleteMovesThatPutKingInCheck(ArrayList<Move> moves) {
        ArrayList<Move> legalMoves = new ArrayList<>(moves.size());

        for(Move move : moves) {
            board.makeMove(move);

            // isKingInCheck RETURNS FALSE EVERY TIME
            if(!board.isKingInCheck())
                legalMoves.add(move);

            board.unmakeMove();
        }

        return legalMoves;
    }

}
