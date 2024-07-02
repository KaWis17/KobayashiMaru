package org.example.Engine.Search;

import junit.framework.TestCase;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.Engine;

public class MiddleSearcherTest extends TestCase {

    public void testSearch() {
        Engine engine = new Engine();
        engine.initiateDefaultPosition();

        MiddleSearcher middleSearcher = new MiddleSearcher(null, null, null);
        middleSearcher.search();
    }
}