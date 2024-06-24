package org.example.Engine.BoardRepresentation;

public class FenImplementer implements BoardConstants{

    public static void implement(Board board, String fen) {
        String[] fenSplit = fen.split(" ", 2);

        board.state = GAME_STATE.EARLY_GAME;
        fillValues(board, fenSplit[1]);
        fillFormats(board, fenSplit[0]);
    }

    public static String generateFEN(Board board) {
        return getBoardFen(board) + " " + getValuesFen(board);
    }

    private static void fillValues(Board board, String leftoverFen) {
        String[] leftoverSplit = leftoverFen.split(" ");

        board.whiteToMove = (leftoverSplit[0].equals("w"));

        board.canWhiteCastleKingside = (leftoverSplit[1].contains("K"));
        board.canWhiteCastleQueenside = (leftoverSplit[1].contains("Q"));
        board.canBlackCastleKingside = (leftoverSplit[1].contains("k"));
        board.canBlackCastleQueenside = (leftoverSplit[1].contains("q"));

        board.enPassantTarget = SquareCalculator.calculate(leftoverSplit[2]);

        board.halfMoveClock = Short.parseShort(leftoverSplit[3]);
        board.fullMoveNumber = Short.parseShort(leftoverSplit[4]);
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

    private static String getBoardFen(Board board) {
        StringBuilder fen = new StringBuilder();
        for(short i=64; i>=1; i-=8) {
            int counter = 0;
            for(short j=i; j>i-8; j--) {
                switch(board.arrayRepresentation.getPieceOnSquare(j)) {
                    case WHITE | PAWN -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("P");
                    }
                    case BLACK | PAWN -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("p");
                    }
                    case WHITE | BISHOP -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("B");
                    }
                    case BLACK | BISHOP -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("b");
                    }
                    case WHITE | KNIGHT -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("N");
                    }
                    case BLACK | KNIGHT -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("n");
                    }
                    case WHITE | ROOK -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("R");
                    }
                    case BLACK | ROOK -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("r");
                    }
                    case WHITE | QUEEN -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("Q");
                    }
                    case BLACK | QUEEN -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("q");
                    }
                    case WHITE | KING -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("K");
                    }
                    case BLACK | KING -> {
                        if(counter != 0){
                            fen.append(counter);
                            counter = 0;
                        }
                        fen.append("k");
                    }
                    case 0 -> counter++;
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

        if(board.whiteToMove) fen.append("w ");
        else fen.append("b ");

        if(board.canWhiteCastleKingside) fen.append("K");
        if(board.canWhiteCastleQueenside) fen.append("Q");
        if(board.canBlackCastleKingside) fen.append("k");
        if(board.canBlackCastleQueenside) fen.append("q");

        if(board.enPassantTarget == 0) fen.append(" - ");
        else fen.append(" ").append(SquareCalculator.calculate(board.enPassantTarget)).append(" ");

        fen.append(board.halfMoveClock).append(" ");
        fen.append(board.fullMoveNumber);

        return fen.toString();
    }
}
