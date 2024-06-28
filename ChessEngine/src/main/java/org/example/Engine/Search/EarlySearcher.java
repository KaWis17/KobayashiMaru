package org.example.Engine.Search;

import org.example.Engine.Args;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.FenImplementer;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.UciSender;

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
    public Move search() {
        /*
        String currentFen = FenImplementer.BoardToFEN(board);
        currentFen = currentFen.substring(0, currentFen.lastIndexOf(" "));
        currentFen = currentFen.substring(0, currentFen.lastIndexOf(" "));
        currentFen = currentFen.substring(0, currentFen.lastIndexOf(" "));
        currentFen = currentFen + " -";

        ArrayList<String> moves = map.get(currentFen);

        if(moves == null){
            if (Args.DEBUG_ON)
                UciSender.sendDebugMessage("No moves found in the opening book for the current position");
            return null;
        }

        String selectedString = moves.get(random.nextInt(moves.size()));

        return new Move(selectedString, board);

         */
        return new Move("e2e4", board);
    }

    private void fillHashMap(String path) {
        //TODO: implement reading from file
    }
}
