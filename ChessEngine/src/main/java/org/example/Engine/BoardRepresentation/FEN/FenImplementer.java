package org.example.Engine.BoardRepresentation.FEN;

import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.State.State;

import java.util.Map;

public class FenImplementer implements BoardHelper {

    public static void FENToBoard(Board board, String fen) {
        String[] fenSplit = fen.split(" ", 2);

        board.currentBoardState = new State();

        board.currentBoardState.gameState = GAME_STATE.EARLY_GAME;

        fillValues(board, fenSplit[1]);
        fillFormats(board, fenSplit[0]);
    }

    public static String BoardToFEN(Board board) {
        return getBoardFen(board) + " " + getValuesFen(board);
    }

    private static void fillValues(Board board, String leftoverFen) {
        String[] leftoverSplit = leftoverFen.split(" ");

        State boardState = board.currentBoardState;

        boardState.whiteToMove = (leftoverSplit[0].equals("w"));

        boardState.canWhiteCastleKingside = (leftoverSplit[1].contains("K"));
        boardState.canWhiteCastleQueenside = (leftoverSplit[1].contains("Q"));
        boardState.canBlackCastleKingside = (leftoverSplit[1].contains("k"));
        boardState.canBlackCastleQueenside = (leftoverSplit[1].contains("q"));

        boardState.enPassantTarget = BoardHelper.squareStringToNumber(leftoverSplit[2]);

        boardState.halfMoveClock = Short.parseShort(leftoverSplit[3]);
        boardState.fullMoveNumber = Short.parseShort(leftoverSplit[4]);

        boardState.moveThatTookToThisPosition = null;
    }


    private static void fillFormats(Board board, String boardFen) {
        board.clearBoard();

        short squareNumber = 64;

        for(char c: boardFen.toCharArray()) {

            if(Character.isDigit(c))
                squareNumber -= (short) (Character.getNumericValue(c));

            else if(c != '/') {
                switch (c) {
                    case 'P' -> board.addPieceOnSquare(squareNumber, WHITE, PAWN);
                    case 'p' -> board.addPieceOnSquare(squareNumber, BLACK, PAWN);
                    case 'B' -> board.addPieceOnSquare(squareNumber, WHITE, BISHOP);
                    case 'b' -> board.addPieceOnSquare(squareNumber, BLACK, BISHOP);
                    case 'N' -> board.addPieceOnSquare(squareNumber, WHITE, KNIGHT);
                    case 'n' -> board.addPieceOnSquare(squareNumber, BLACK, KNIGHT);
                    case 'R' -> board.addPieceOnSquare(squareNumber, WHITE, ROOK);
                    case 'r' -> board.addPieceOnSquare(squareNumber, BLACK, ROOK);
                    case 'Q' -> board.addPieceOnSquare(squareNumber, WHITE, QUEEN);
                    case 'q' -> board.addPieceOnSquare(squareNumber, BLACK, QUEEN);
                    case 'K' -> board.addPieceOnSquare(squareNumber, WHITE, KING);
                    case 'k' -> board.addPieceOnSquare(squareNumber, BLACK, KING);
                }
                squareNumber--;
            }
        }
    }

    private static final Map<Integer, Character> PIECE_TO_FEN = Map.ofEntries(
            Map.entry(WHITE | PAWN, 'P'),
            Map.entry(BLACK | PAWN, 'p'),
            Map.entry(WHITE | BISHOP, 'B'),
            Map.entry(BLACK | BISHOP, 'b'),
            Map.entry(WHITE | KNIGHT, 'N'),
            Map.entry(BLACK | KNIGHT, 'n'),
            Map.entry(WHITE | ROOK, 'R'),
            Map.entry(BLACK | ROOK, 'r'),
            Map.entry(WHITE | QUEEN, 'Q'),
            Map.entry(BLACK | QUEEN, 'q'),
            Map.entry(WHITE | KING, 'K'),
            Map.entry(BLACK | KING, 'k')
    );

    private static String getBoardFen(Board board) {
        StringBuilder fen = new StringBuilder();
        for(short i=64; i>=1; i-=8) {
            int counter = 0;
            for(short j=i; j>i-8; j--) {
                int piece = board.arrayRepresentation.getPieceOnSquare(j);
                if (piece == 0) {
                    counter++;
                } else {
                    if(counter != 0){
                        fen.append(counter);
                        counter = 0;
                    }
                    fen.append(PIECE_TO_FEN.get(piece));
                }
            }
            if(counter!=0) fen.append(counter);
            fen.append("/");
        }
        fen.deleteCharAt(fen.length()-1);
        return fen.toString();
    }

    private static String getValuesFen(Board board){
        StringBuilder fen = new StringBuilder();

        State boardState = board.currentBoardState;

        if(board.isWhiteToPlay()) fen.append("w ");
        else fen.append("b ");

        fen.append((boardState.canWhiteCastleKingside || boardState.canWhiteCastleQueenside || boardState.canBlackCastleKingside || boardState.canBlackCastleQueenside) ? "" : "-" );
        if(boardState.canWhiteCastleKingside) fen.append("K");
        if(boardState.canWhiteCastleQueenside) fen.append("Q");
        if(boardState.canBlackCastleKingside) fen.append("k");
        if(boardState.canBlackCastleQueenside) fen.append("q");

        if(boardState.enPassantTarget == 0) fen.append(" - ");
        else fen.append(" ").append(BoardHelper.squareNumberToString(boardState.enPassantTarget)).append(" ");

        fen.append(boardState.halfMoveClock).append(" ");
        fen.append(boardState.fullMoveNumber);

        return fen.toString();
    }
}
