package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranspositionTable {
    LRU<Long, TranspositionResult[]> cache = new LRU<>(5_000_000);

    public void put(long hash, int depth, int score, boolean isWhiteToPlay){
        if(!Config.TRANSPOSITION_TABLE_ON)
            return;

        int side = (isWhiteToPlay) ? 0 : 1;
        cache.computeIfAbsent(hash, k -> new TranspositionResult[2]);
        TranspositionResult currentValue = cache.get(hash)[side];
        if(currentValue != null && currentValue.depth >= depth)
            return;

        cache.get(hash)[side] = new TranspositionResult(depth, score);
    }

    public Integer get(long hash, int minimumAcceptableDepth, boolean isWhiteToEvaluate){
        if(!Config.TRANSPOSITION_TABLE_ON)
            return null;

        int side = isWhiteToEvaluate ? 0 : 1;
        TranspositionResult[] result = cache.get(hash);
        if(result == null || result[side] == null)
            return null;
        if(result[side].depth >= minimumAcceptableDepth) {
            return result[side].score;
        }

        return null;
    }

    @Override
    public String toString() {
        return cache.toString();
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

class LRU<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRU(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this.capacity;
    }
}
