package org.example.Engine.BoardRepresentation.BoardFormats;

public interface Format {
    void addPieceOnSquare(short square, short color, short piece);
    void deletePieceOnSquare(short square, short color, short piece);
    void clearBoard();
}
