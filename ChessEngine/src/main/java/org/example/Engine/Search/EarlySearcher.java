package org.example.Engine.Search;

import org.example.Engine.BoardRepresentation.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EarlySearcher implements Search {
    HashMap<String, ArrayList<String>> map = new HashMap<>();
    Random random = new Random();

    Board board;

    EarlySearcher(Board board) {
        this.board = board;
        fillHashMap("Resources/OpeningLibrary");
    }

    @Override
    public void search() {

    }

    private void fillHashMap(String path) {
        //TODO: implement reading from file
    }
}
