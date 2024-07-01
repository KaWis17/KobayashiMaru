package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.MoveGeneration.PieceGenerators.Generator;

public class AttackOfKingGenerator implements BoardConstants {

    Board board;
    MoveGenerator mg;

    short numberOfChecks;
    long dangerMask;

    public AttackOfKingGenerator(Board board, MoveGenerator mg) {
        this.board = board;
        this.mg = mg;
    }

    public void checkForChecks(short myColor) {
        short opponentColor = myColor == WHITE ? BLACK : WHITE;
        long myKing = board.getSpecificPiecesBitBoard((short) (KING | myColor));
        long allMyColor = board.getSpecificPiecesBitBoard(myColor);
        long allOpponentColor = board.getSpecificPiecesBitBoard(opponentColor);
        long allEmpty = ~(allMyColor | allOpponentColor);

        long kingAsKnight = mg.knightMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);
        long kingAsSlidingPiece = mg.slidingMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);
        long kingAsPawn = mg.pawnMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);

        long dangerMask = kingAsKnight | kingAsSlidingPiece | kingAsPawn;

        short numberOfChecks = 0;

        long next = dangerMask & -dangerMask;
        while (next != 0) {

            numberOfChecks++;
            dangerMask &=~ (next);
            next = dangerMask & -dangerMask;
        }

        this.numberOfChecks = numberOfChecks;
        this.dangerMask = dangerMask;
    }

    public boolean isKingInCheck(short myColor) {

        short opponentColor = myColor == WHITE ? BLACK : WHITE;
        long myKing = board.getSpecificPiecesBitBoard((short) (KING | myColor));
        long allMyColor = board.getSpecificPiecesBitBoard(myColor);
        long allOpponentColor = board.getSpecificPiecesBitBoard(opponentColor);
        long allEmpty = ~(allMyColor | allOpponentColor);

        if (myKing == 0L) {
            return true;
        }

        if (board.getSpecificPiecesBitBoard((short) (KING | opponentColor)) == 0L) {
            return true;
        }

        long kingAsKnight = mg.knightMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsKnight != 0){
            return true;
        }

        long kingAsSlidingPiece = mg.slidingMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsSlidingPiece != 0) {
            return true;
        }

        long kingAsPawn = mg.pawnMoveGenerator.getKingAsFigureDangerMask(myColor, myKing, allMyColor, allOpponentColor, allEmpty);
        if(kingAsPawn != 0)
            return true;

        return false;


    }

}
