package org.example.Engine.BoardRepresentation;

import org.example.Engine.MoveGeneration.PieceGenerators.Generator;
import org.example.Engine.MoveGeneration.PieceGenerators.KnightMoveGenerator;
import org.example.Engine.MoveGeneration.PieceGenerators.PawnMoveGenerator;
import org.example.Engine.MoveGeneration.PieceGenerators.SlidingMoveGenerator;

public class CheckChecker implements BoardHelper{

    Board board;
    Generator kingMoveGenerator;
    Generator knightMoveGenerator;
    Generator pawnMoveGenerator;
    Generator slidingMoveGenerator;

    public CheckChecker(Board board) {
        this.board = board;
        kingMoveGenerator = new KnightMoveGenerator(board);
        knightMoveGenerator = new KnightMoveGenerator(board);
        pawnMoveGenerator = new PawnMoveGenerator(board);
        slidingMoveGenerator = new SlidingMoveGenerator(board);
    }

    public boolean isWhiteInCheck(){
        return isColorInCheck(WHITE);
    }

    public boolean isBlackInCheck() {
        return isColorInCheck(BLACK);
    }

    public boolean isColorInCheck(byte color){
        byte opponentColor = color == WHITE ? BLACK : WHITE;

        long myKing = board.getSpecificBitBoard((byte) (KING | color));

        long allMyColor = board.getSpecificBitBoard(color);
        long allOpponentColor = board.getSpecificBitBoard(opponentColor);

        long allEmpty = ~(allMyColor | allOpponentColor);

        if (myKing == 0L)
            return true;

        if (board.getSpecificBitBoard((byte) (KING | opponentColor)) == 0L)
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
}
