package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.State;
import org.example.Engine.MoveGeneration.PieceGenerators.*;

import java.util.ArrayList;

public class MoveGenerator implements BoardConstants {

    Board board;
    Generator pawnMoveGenerator;
    Generator knightMoveGenerator;
    Generator kingMoveGenerator;
    Generator slidingMoveGenerator;

    AttackOfKingGenerator checkGenerator;

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

        //return moves;
        return deleteMovesThatPutKingInCheck(moves, color);
    }

    private ArrayList<Move> deleteMovesThatPutKingInCheck(ArrayList<Move> moves, short color) {
       ArrayList<Move> legalMoves = new ArrayList<>(40);

        for(Move move : moves) {
//            System.out.println(move + "   " + move.type);
//            System.out.println(board.currentBoardState.whiteToMove);
//            if(!board.stateHistory.isEmpty())
//                System.out.println(board.stateHistory.peek().moveMade);
//            if(board.stateHistory.size() > 1) {
//                State state = board.stateHistory.pop();
//                System.out.println(board.stateHistory.peek().moveMade);
//                System.out.println(board.stateHistory.add(state));
//            }

            //Generator.printMask(board.getSpecificPiecesBitBoard((short) (BLACK|BoardConstants.PAWN)));
            board.makeMove(move);
            if(!checkGenerator.isKingInCheck(color))
                legalMoves.add(move);
            board.unmakeMove();
        }

        return legalMoves;
    }


}
