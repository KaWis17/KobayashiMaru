package org.example.Engine.BoardRepresentation;

import java.util.Random;

public class ZobristHashing {

    Board board;
    Random random;

    private long hash;
    private long transpositionHash;
    long[][] boardRandoms;
    long blackRandom;
    long drawRandom;

    ZobristHashing(Board board) {
        this.board = board;
        random = new Random(1294811425);

        boardRandoms = new long[64][15];

        for(int i=0; i<64; i++){
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.PAWN] = random.nextLong();
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.BISHOP] = random.nextLong();
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.KNIGHT] = random.nextLong();
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.ROOK] = random.nextLong();
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.QUEEN] = random.nextLong();
            boardRandoms[i][BoardHelper.WHITE | BoardHelper.KING] = random.nextLong();

            boardRandoms[i][BoardHelper.BLACK | BoardHelper.PAWN] = random.nextLong();
            boardRandoms[i][BoardHelper.BLACK | BoardHelper.BISHOP] = random.nextLong();
            boardRandoms[i][BoardHelper.BLACK | BoardHelper.KNIGHT] = random.nextLong();
            boardRandoms[i][BoardHelper.BLACK | BoardHelper.ROOK] = random.nextLong();
            boardRandoms[i][BoardHelper.BLACK | BoardHelper.QUEEN] = random.nextLong();
            boardRandoms[i][BoardHelper.BLACK | BoardHelper.KING] = random.nextLong();
        }
        blackRandom = random.nextLong();
        drawRandom = random.nextLong();
    }

    public void createNew() {
        hash = 0L;
        if(!board.isWhiteToPlay())
            hash = hash ^ blackRandom;

        for(byte i=1; i<=64; i++){
            int piece = board.getPieceOnSquare(i);
            if(piece != 0)
                hash = hash ^ boardRandoms[64 - i][piece];
        }
        transpositionHash = hash;
    }

    public void addOnSquare(byte square, byte color, byte piece) {
        hash = hash ^ boardRandoms[64 - square][color | piece];
        transpositionHash = transpositionHash ^ boardRandoms[64 - square][color | piece];
    }

    public void deleteOnSquare(byte square, byte color, byte piece) {
        hash = hash ^ boardRandoms[64 - square][color | piece];
        transpositionHash = transpositionHash ^ boardRandoms[64 - square][color | piece];
    }

    public void updateColor() {
        hash = hash ^ blackRandom;
        transpositionHash = transpositionHash ^ blackRandom;
    }

    public void clear(){
        hash = 0;
        transpositionHash = 0;
    }

    public long getHash(){
        return hash;
    }

    public long getTranspositionHash(){
        return transpositionHash;
    }

    public void updateTranspositionHash() {
        transpositionHash = transpositionHash ^ drawRandom;
    }

}
