package org.example.Engine.MoveGeneration;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;
import org.example.Engine.BoardRepresentation.Move.Move;

import java.util.ArrayList;

public class KingMoveGenerator extends Generator implements BoardConstants, Constants {

    long king, sameColor;

    public ArrayList<Move> generateMoves(Board board) {
        possibleMoves = new ArrayList<>();

        if(board.whiteToMove) {
            king = board.bitBoards.bitBoards[WHITE | KING];
            sameColor = board.bitBoards.bitBoards[WHITE];
        }
        else {
            king = board.bitBoards.bitBoards[BLACK | KING];
            sameColor = board.bitBoards.bitBoards[BLACK];
        }

        move(board.whiteToMove);

        return possibleMoves;
    }

    public void move(boolean color) {
        short index = (short) (Long.numberOfLeadingZeros(king) + 1);

        boolean notInRank1 = ((king & rank1) == 0);
        boolean notInRank8 = ((king & rank8) == 0);

        boolean notInFileA = ((king & fileA) == 0);
        boolean notInFileH = ((king & fileH) == 0);

        long mask = 0;

        if(notInRank1) mask = mask | emptyBinary + 1 << 64-index-8; //D
        if(notInFileH) mask = mask | emptyBinary + 1 << 64-index-1; //R
        if(notInRank1 & notInFileH) mask = mask | emptyBinary + 1 << 64-index-9; //DR
        if(notInRank1 & notInFileA) mask = mask | emptyBinary + 1 << 64-index-7; //DL

        if(notInRank8) mask = mask | emptyBinary + 1 << 64-index+8; //U
        if(notInFileA) mask = mask | emptyBinary + 1 << 64-index+1; //L
        if(notInRank8 & notInFileA) mask = mask | emptyBinary + 1 << 64-index+9; //UL
        if(notInRank8 & notInFileH) mask = mask | emptyBinary + 1 << 64-index+7; //UR

        mask = mask & ~sameColor;
    }

}
