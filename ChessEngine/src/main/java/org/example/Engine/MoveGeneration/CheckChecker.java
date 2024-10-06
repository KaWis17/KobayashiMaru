package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.MoveGeneration.PieceGenerators.*;

import java.util.List;

public class CheckChecker {

    Board board;
    Generator kingMoveGenerator;
    Generator knightMoveGenerator;
    Generator pawnMoveGenerator;
    Generator slidingMoveGenerator;

    MoveGenerator generator;

    public CheckChecker(Board board) {
        this.board = board;
        generator = new MoveGenerator(board);
        kingMoveGenerator = new KingMoveGenerator(board);
        knightMoveGenerator = new KnightMoveGenerator(board);
        pawnMoveGenerator = new PawnMoveGenerator(board);
        slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public boolean isWhiteInCheck(){
        return isColorInCheck(board.WHITE);
    }

    public boolean isWhiteInCheckMate(){
        return isCheckMate(board.WHITE);
    }

    public boolean isBlackInCheck() {
        return isColorInCheck(board.BLACK);
    }

    public boolean isBlackInCheckMate(){
        return isCheckMate(board.BLACK);
    }

    public boolean isColorInCheck(byte color){
        byte opponentColor = color == board.WHITE ? board.BLACK : board.WHITE;

        long myKing = board.getSpecificBitBoard((byte) (board.KING | color));

        long allMyColor = board.getSpecificBitBoard(color);
        long allOpponentColor = board.getSpecificBitBoard(opponentColor);

        long allEmpty = ~(allMyColor | allOpponentColor);

        if (myKing == 0L)
            return true;

        if (board.getSpecificBitBoard((byte) (board.KING | opponentColor)) == 0L)
            return true;

        long kingAsOpponentKing = kingMoveGenerator.getKingAsFigureDangerMask(color, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsOpponentKing != 0)
            return true;

        long kingAsKnight = knightMoveGenerator.getKingAsFigureDangerMask(color, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsKnight != 0)
            return true;

        long kingAsSlidingPiece = slidingMoveGenerator.getKingAsFigureDangerMask(color, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsSlidingPiece != 0)
            return true;

        long kingAsPawn = pawnMoveGenerator.getKingAsFigureDangerMask(color, myKing, allMyColor, allOpponentColor, allEmpty);
        return kingAsPawn != 0;
    }

    public boolean isCheckMate(byte color) {
        if (!isColorInCheck(color)) {
            return false;
        }

        // Generate all possible moves for the given color
        List<Move> allPossibleMoves = generator.generateAllPseudoLegalMoves();

        // For each move, make the move and check if the king is still in check
        for (Move move : allPossibleMoves) {
            if(board.makeMove(move)) {
                boolean stillInCheck = isColorInCheck(color);
                board.unmakeMove();

                if (!stillInCheck) {
                    return false;
                }
            }
            else {
                board.unmakeMove();
            }
        }

        return true;
    }

}
