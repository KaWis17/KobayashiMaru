package org.example.Engine.Search.EarlySearcher;

import org.example.Engine.Args.Config;
import org.example.Engine.BoardRepresentation.Board;
import org.example.Engine.BoardRepresentation.BoardHelper;
import org.example.Engine.BoardRepresentation.Move.Move;
import org.example.Engine.Search.Search;
import org.example.Engine.Search.Searcher;
import org.example.UciSender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EarlySearcher implements Search {

    Board board;
    Searcher searcher;
    HashMap<String, String[]> openingLibrary = new HashMap<>();

    // Format of the library is the same as used by Sebastian Lague.
    // https://github.com/SebLague/Chess-Coding-Adventure

    public EarlySearcher(Board board, Searcher searcher) {

        this.board = board;
        this.searcher = searcher;
        loadOpeningLibrary();
    }

    private void loadOpeningLibrary() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Config.OPENING_LIBRARY_LOCATION));
            String line;
            String fen = "";
            List<String> moves = new ArrayList<>();

            while((line = reader.readLine()) != null) {

                if(line.startsWith("pos")){
                    if(!moves.isEmpty()){
                        String[] arrayOfMoves = moves.toArray(new String[0]);
                        openingLibrary.put(fen, arrayOfMoves);
                        moves.clear();
                    }
                    fen = line.replace("pos ", "");
                }
                else
                    moves.add(line.split(" ")[0]);
            }
            if (!moves.isEmpty()) {
                String[] arrayOfMoves = moves.toArray(new String[0]);
                openingLibrary.put(fen, arrayOfMoves);
            }

        } catch (IOException ignored) {

        }
    }

    @Override
    public void search() {
        String fen = board.boardToLibraryFEN();
        UciSender.sendDebugMessage("Searching for string: " + fen);
        String[] moves = openingLibrary.get(fen);
        UciSender.sendDebugMessage("Found: " + Arrays.toString(moves));

        if(moves == null || moves.length == 0) {
            searcher.isLibrarySearchComplete = true;
            searcher.search();
        }
        else {
            int rnd = new Random().nextInt(moves.length);
            searcher.bestMove = new Move(moves[rnd], board);
        }
    }
}
