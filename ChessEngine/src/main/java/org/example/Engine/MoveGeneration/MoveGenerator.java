package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.PieceGenerators.*;

import java.util.ArrayList;

public class MoveGenerator implements BoardHelper {

    Board board;
    Generator pawnMoveGenerator;
    Generator knightMoveGenerator;
    Generator kingMoveGenerator;
    Generator slidingMoveGenerator;

    public MoveGenerator(Board board) {
        this.board = board;
        pawnMoveGenerator = new PawnMoveGenerator(board);
        knightMoveGenerator = new KnightMoveGenerator(board);
        kingMoveGenerator = new KingMoveGenerator(board);
        slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public ArrayList<Move> generateAllLegalMoves() {
        if(board.isDrawByRepetition())
            return new ArrayList<>();

        ArrayList<Move> moves = generateAllPseudoLegalMoves();

        return deleteMovesThatPutKingInCheck(moves);
    }

    public ArrayList<Move> generateAllLegalCaptureMoves() {
        if(board.isDrawByRepetition())
            return new ArrayList<>();

        ArrayList<Move> moves = generateAllPseudoLegalCaptureMoves();

        return deleteMovesThatPutKingInCheck(moves);
    }

    public ArrayList<Move> generateAllPseudoLegalMoves() {
        ArrayList<Move> moves = new ArrayList<>(40);

        boolean whiteToMove = board.isWhiteToPlay();
        byte color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.getSpecificBitBoard(WHITE) : board.getSpecificBitBoard(BLACK);
        long opponentPieces = whiteToMove ? board.getSpecificBitBoard(BLACK) : board.getSpecificBitBoard(WHITE);
        long emptySquares = ~(myPieces | opponentPieces);

        moves.addAll(kingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(pawnMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(knightMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(slidingMoveGenerator.generateMoves(color, myPieces, opponentPieces, emptySquares));

        return moves;
    }

    public ArrayList<Move> generateAllPseudoLegalCaptureMoves() {
        ArrayList<Move> moves = new ArrayList<>(20);

        boolean whiteToMove = board.isWhiteToPlay();
        byte color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.getSpecificBitBoard(WHITE) : board.getSpecificBitBoard(BLACK);
        long opponentPieces = whiteToMove ? board.getSpecificBitBoard(BLACK) : board.getSpecificBitBoard(WHITE);
        long emptySquares = ~(myPieces | opponentPieces);

        moves.addAll(kingMoveGenerator.generateCaptureMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(pawnMoveGenerator.generateCaptureMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(knightMoveGenerator.generateCaptureMoves(color, myPieces, opponentPieces, emptySquares));
        moves.addAll(slidingMoveGenerator.generateCaptureMoves(color, myPieces, opponentPieces, emptySquares));

        return moves;
    }

    private ArrayList<Move> deleteMovesThatPutKingInCheck(ArrayList<Move> moves) {
       ArrayList<Move> legalMoves = new ArrayList<>(40);

        for(Move move : moves) {
            board.makeMove(move);
            if(!board.isOpponentColorInCheck())
                legalMoves.add(move);
            board.unmakeMove();
        }

        return legalMoves;
    }
}
