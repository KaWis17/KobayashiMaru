package org.example.Engine.BoardRepresentation.BoardFormats;

public interface Format {
    void addPieceOnSquare(byte square, byte color, byte piece);
    void deletePieceOnSquare(byte square, byte color, byte piece);
    void clearBoard();
}
