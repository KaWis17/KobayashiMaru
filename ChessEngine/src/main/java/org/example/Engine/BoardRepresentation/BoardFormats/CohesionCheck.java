package org.example.Engine.BoardRepresentation.BoardFormats;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardConstants;

import java.util.Arrays;

public class CohesionCheck implements BoardConstants {

    public static boolean isCohesive(Board board) {
        ArrayRepresentation arrayRepresentation = board.arrayRepresentation;
        BitBoardsRepresentation bitBoardsRepresentation = board.bitBoardsRepresentation;

        if(piecesAreInvalid(arrayRepresentation, bitBoardsRepresentation, WHITE)) return false;
        if(piecesAreInvalid(arrayRepresentation, bitBoardsRepresentation, BLACK)) return false;
        if(colorsAreInvalid(arrayRepresentation, bitBoardsRepresentation, WHITE)) return false;
        if(colorsAreInvalid(arrayRepresentation, bitBoardsRepresentation, BLACK)) return false;
        return true;
    }

    private static boolean piecesAreInvalid(ArrayRepresentation arrayRepresentation, BitBoardsRepresentation bitBoardsRepresentation, short color) {
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | PAWN], color | PAWN, arrayRepresentation.board)) return true;
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | ROOK], color | ROOK, arrayRepresentation.board)) return true;
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | KNIGHT], color | KNIGHT, arrayRepresentation.board)) return true;
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | BISHOP], color | BISHOP, arrayRepresentation.board)) return true;
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | QUEEN], color | QUEEN, arrayRepresentation.board)) return true;
        if(checkIfSingleBoardInvalid(bitBoardsRepresentation.bitBoards[color | KING], color | KING, arrayRepresentation.board)) return true;

        return false;
    }

    private static boolean colorsAreInvalid(ArrayRepresentation arrayRepresentation, BitBoardsRepresentation bitBoardsRepresentation, short color) {
        String boardString = String.format("%64s", Long.toBinaryString(bitBoardsRepresentation.bitBoards[color])).replace(' ', '0');

        for(int i = 0; i < 64; i++) {
            if(boardString.charAt(i) == '1') {
                if(arrayRepresentation.board[i] < color+1 || arrayRepresentation.board[i] > color+6) {
                    return true;
                }
            }
            else {
                if(arrayRepresentation.board[i] >= color+1 && arrayRepresentation.board[i] <= color+6) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkIfSingleBoardInvalid(long board, int pieceType, byte[] boardArray) {
        String boardString = String.format("%64s", Long.toBinaryString(board)).replace(' ', '0');

        for(int i = 0; i < 64; i++) {
            if(boardString.charAt(i) == '1') {
                if(boardArray[i] != pieceType) {
                    return true;
                }
            }
            else {
                if(boardArray[i] == pieceType) {
                    return true;
                }
            }
        }
        return false;
    }
}
