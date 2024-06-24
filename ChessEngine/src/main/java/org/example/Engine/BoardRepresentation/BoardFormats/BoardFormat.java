package org.example.Engine.BoardRepresentation.BoardFormats;

import org.example.Engine.BoardRepresentation.Move.Move;

public interface BoardFormat {
    void addPieceOnSquare(short square, short color, short piece);
    void deletePieceOnSquare(short square, short color, short piece);
    void clearBoard();
}
