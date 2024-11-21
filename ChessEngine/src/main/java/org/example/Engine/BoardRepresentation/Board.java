package org.example.Engine.BoardRepresentation;

import org.example.Engine.BoardRepresentation.BoardFormats.ArrayRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BitBoardsRepresentation;
import org.example.Engine.BoardRepresentation.BoardFormats.BoardFormat;
import org.example.Engine.BoardRepresentation.BoardFormats.CountRepresentation;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.BoardRepresentation.Move.MoveMaker;
import org.example.Engine.BoardRepresentation.Move.MoveUnMaker;
import org.example.Engine.MoveGeneration.CheckChecker;

import java.util.HashMap;
import java.util.Stack;

public class Board implements BoardHelper {

    public State currentBoardState;
    public Stack<State> stateHistory = new Stack<>();

    public BoardFormat bitBoardsRepresentation = new BitBoardsRepresentation();
    public BoardFormat arrayRepresentation = new ArrayRepresentation();
    public BoardFormat countRepresentation = new CountRepresentation();

    MoveMaker moveMaker = new MoveMaker(this);
    MoveUnMaker moveUnMaker = new MoveUnMaker(this);

    CheckChecker checkChecker = new CheckChecker(this);
    public ZobristHashing zobristHashing = new ZobristHashing(this);

    public HashMap<Long, Integer> positionCount = new HashMap<>();

    public void newGame() {
        stateHistory.clear();
        positionCount.clear();
    }

    public void startFromDefaultPosition() {
        startFromCustomPosition(BoardHelper.STARTING_FEN);
    }

    public void startFromCustomPosition(String fen) {
        BoardHelper.FENToBoard(this, fen);
    }

    public boolean makeMove(Move moveToMake) {
        moveMaker.makeMove(moveToMake);
        return !isOpponentColorInCheck();
    }

    public boolean makeMove(String moveToMake) {
        Move move = new Move(moveToMake, this);
        moveMaker.makeMove(move);

        return !isOpponentColorInCheck();
    }

    public void unmakeMove() {
        moveUnMaker.unmakeMove();
    }

    public void addPieceOnSquare(byte square, byte color, byte piece) {
        bitBoardsRepresentation.addPieceOnSquare(square, color, piece);
        arrayRepresentation.addPieceOnSquare(square, color, piece);
        countRepresentation.addPieceOnSquare(square, color, piece);
        zobristHashing.addOnSquare(square, color, piece);
    }

    public void deletePieceFromSquare(byte square, byte color, byte piece) {
        bitBoardsRepresentation.deletePieceOnSquare(square, color, piece);
        arrayRepresentation.deletePieceOnSquare(square, color, piece);
        countRepresentation.deletePieceOnSquare(square, color, piece);
        zobristHashing.deleteOnSquare(square, color, piece);
    }

    public void clearBoard() {
        bitBoardsRepresentation.clearBoard();
        arrayRepresentation.clearBoard();
        countRepresentation.clearBoard();
        zobristHashing.clear();
    }

    public byte getPieceOnSquare(byte square) {
        return ((ArrayRepresentation) arrayRepresentation).getPieceOnSquare(square);
    }

    public byte getEnPassantTarget() {
        return currentBoardState.enPassantTarget;
    }

    public long getSpecificBitBoard(byte piece) {
        return ((BitBoardsRepresentation) bitBoardsRepresentation).bitBoards[piece];
    }

    public int getCurrentMoveNumber() {
        return currentBoardState.fullMoveNumber;
    }

    public boolean isWhiteToPlay() {
        return currentBoardState.whiteToMove;
    }

    public boolean isOpponentColorInCheck() {
        return checkChecker.isColorInCheck(currentBoardState.whiteToMove ? BLACK : WHITE);
    }

    public boolean isWhiteInCheck() {
        return checkChecker.isWhiteInCheck();
    }

    public boolean isWhiteInCheckMate() {
        return checkChecker.isWhiteInCheckMate();
    }

    public boolean isBlackInCheck() {
        return checkChecker.isBlackInCheck();
    }

    public boolean isBlackInCheckMate() {
        return checkChecker.isBlackInCheckMate();
    }


    public boolean isDraw() {
        return isDrawByRepetition() || isDrawByFiftyMoves();
    }

    public boolean isDrawByFiftyMoves() {
        return currentBoardState.halfMoveClock >= 100;
    }

    public boolean isDrawByRepetition() {
        Integer value = positionCount.get(zobristHashing.getHash());
        if (value == null)
                return false;
        return value >= 3;
    }

    public int getPieceCount(int piece) {
        return ((CountRepresentation)countRepresentation).pieces[piece];
    }

    public byte squareStringToNumber(String square){
        return BoardHelper.squareStringToNumber(square);
    }

    public String squareNumberToString(byte square) {
        return BoardHelper.squareNumberToString(square);
    }

    public String boardToLibraryFEN() {
        return BoardHelper.BoardToLibraryFEN(this);
    }

    public byte getPieceType(byte piece){
        return BoardHelper.getPieceType(piece);
    }

    public static byte getPieceColor(byte piece){
        return BoardHelper.getPieceColor(piece);
    }

    @Override
    public String toString() {
        return display();
    }

    private String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("BOARD: ").append(BoardHelper.BoardToFEN(this)).append("\n");
        sb.append("elements in positionCount: ").append(positionCount.size()).append("\n");
        sb.append("is threefold repetition: ").append(isDrawByRepetition()).append("\n");
        sb.append("is threefold repetition: ").append(positionCount).append("\n");
        sb.append("current hash: ").append(zobristHashing.getHash()).append("\n");
        sb.append("current hashValue: ").append(positionCount.get(zobristHashing.getHash())).append("\n");

        for(byte i=64; i>=1; --i) {
            switch(getPieceOnSquare(i)) {
                case WHITE | PAWN -> sb.append("P ");
                case BLACK | PAWN -> sb.append("p ");
                case WHITE | BISHOP -> sb.append("B ");
                case BLACK | BISHOP -> sb.append("b ");
                case WHITE | KNIGHT -> sb.append("N ");
                case BLACK | KNIGHT -> sb.append("n ");
                case WHITE | ROOK -> sb.append("R ");
                case BLACK | ROOK -> sb.append("r ");
                case WHITE | QUEEN -> sb.append("Q ");
                case BLACK | QUEEN -> sb.append("q ");
                case WHITE | KING -> sb.append("K ");
                case BLACK | KING -> sb.append("k ");
                default -> sb.append("_ ");
            }

            if((i-1) % 8 == 0) sb.append("\n");
        }
        return sb.toString();
    }
}