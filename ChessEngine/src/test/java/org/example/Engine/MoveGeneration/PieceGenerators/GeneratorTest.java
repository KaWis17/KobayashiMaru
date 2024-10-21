package org.example.Engine.MoveGeneration.PieceGenerators;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;

import static org.example.Engine.BoardRepresentation.BoardHelper.BLACK;
import static org.example.Engine.BoardRepresentation.BoardHelper.WHITE;

public class GeneratorTest extends TestCase {

    public void testAddMovesFromMask() {
        Board board = new Board();
        board.startFromDefaultPosition();
        board.makeMove("e2e4");
        Generator generator = new PawnMoveGenerator(board);

        boolean whiteToMove = board.isWhiteToPlay();
        byte color = whiteToMove ? WHITE : BLACK;
        long myPieces = whiteToMove ? board.getSpecificBitBoard(WHITE) : board.getSpecificBitBoard(BLACK);
        long opponentPieces = whiteToMove ? board.getSpecificBitBoard(BLACK) : board.getSpecificBitBoard(WHITE);
        long emptySquares = ~(myPieces | opponentPieces);

        generator.generateMoves(color, myPieces, opponentPieces, emptySquares);
    }

    public void testAddMovesFromMaskWithStartIndex() {
    }
}