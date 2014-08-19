package net.skytreader.kode.chesstemplar.pieces;

public interface ChessPiece{
    /**
    Return the new position of this piece, in chess algebraic notation.
    */
    public String move(char col, int row);
    /**
    Return the current position of this piece, in chess algebraic notation.
    */
    public String getCurrentPosition();
}
