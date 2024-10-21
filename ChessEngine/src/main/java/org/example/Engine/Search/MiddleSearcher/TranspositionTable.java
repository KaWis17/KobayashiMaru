package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;

import java.util.HashMap;

public class TranspositionTable {
    HashMap<Long, TranspositionResult[]> transpositionTable = new HashMap<>();

    public void put(long hash, int depth, int score, boolean isWhiteToPlay){
        if(!Config.TRANSPOSITION_TABLE_ON)
            return;

        int side = (isWhiteToPlay) ? 0 : 1;
        transpositionTable.computeIfAbsent(hash, k -> new TranspositionResult[2]);
        TranspositionResult currentValue = transpositionTable.get(hash)[side];
        if(currentValue != null && currentValue.depth >= depth)
            return;

        transpositionTable.get(hash)[side] = new TranspositionResult(depth, score);
    }

    public Integer get(long hash, int minimumAcceptableDepth, boolean isWhiteToEvaluate){
        if(!Config.TRANSPOSITION_TABLE_ON)
            return null;

        int side = isWhiteToEvaluate ? 0 : 1;
        TranspositionResult[] result = transpositionTable.get(hash);
        if(result == null || result[side] == null)
            return null;
        if(result[side].depth >= minimumAcceptableDepth) {
            return result[side].score;
        }

        return null;
    }

    public void reset(){
        transpositionTable = new HashMap<>();
    }

}

class TranspositionResult {
    int depth;
    int score;
    TranspositionResult(int depth, int score){
        this.depth = depth;
        this.score = score;
    }

    @Override
    public String toString() {
        return "TranspositionResult{" +
                "depth=" + depth +
                ", score=" + score +
                '}';
    }
}
