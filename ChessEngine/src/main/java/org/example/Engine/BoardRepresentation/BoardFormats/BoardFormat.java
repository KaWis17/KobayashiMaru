package org.example.Engine.BoardRepresentation.BoardFormats;

public abstract class BoardFormat{
    public abstract void addPieceOnSquare(byte square, byte color, byte piece);
    public abstract void deletePieceOnSquare(byte square, byte color, byte piece);
    public abstract void clearBoard();
}
