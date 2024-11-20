package org.example.Engine.Search.MiddleSearcher;

import org.example.Engine.Args.Config;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranspositionTable {
    LRU<Long, TranspositionResult> cache = new LRU<>(1_000_000);

    public void put(long hash, int depth, int score, TranspositionResult.Flag flag) {
        if (!Config.TRANSPOSITION_TABLE_ON)
            return;

        TranspositionResult currentValue = cache.get(hash);
        if (currentValue != null && currentValue.depth >= depth)
            return;

        if(currentValue != null) {
            currentValue.depth = depth;
            currentValue.score = score;
            currentValue.flag = flag;
            return;
        }

        cache.put(hash, new TranspositionResult(depth, score, flag));
    }

    public TranspositionResult get(long hash, int minimumAcceptableDepth) {
        if (!Config.TRANSPOSITION_TABLE_ON)
            return null;

        TranspositionResult result = cache.get(hash);
        if (result == null || result.depth < minimumAcceptableDepth)
            return null;

        return result;
    }

    @Override
    public String toString() {
        return cache.toString();
    }
}

class TranspositionResult {

    enum Flag {
        EXACT, LOWER_BOUND, UPPER_BOUND
    }

    int depth;
    int score;
    Flag flag;

    TranspositionResult(int depth, int score, Flag flag) {
        this.depth = depth;
        this.score = score;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "TranspositionResult{" +
                "depth=" + depth +
                ", score=" + score +
                ", flag=" + flag +
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