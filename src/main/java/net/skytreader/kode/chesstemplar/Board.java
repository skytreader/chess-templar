package net.skytreader.kode.chesstemplar;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
This interface abstracts Chess boards.

When the board is constructed, all the pieces must be in the initial state
of the game.
*/
public interface Board{
    /**
    Get the piece at the specified row and column. Indexing is zero-based.
    Index 0,0 is the initial position of the black queen-side rook ("a8"). The
    ranks are rows and the files are columns.

    E.g.,
    b8 -> 0, 1

    If the row and column indicated is a blank tile, return null.
    
    @param r
    @param c
    @return The ChessPiece at the indicated coordinates if it is occuppied or
        null if it is not.
    */
    public ChessPiece getPieceAt(int r, int c);

    /**
    Move the piece at the location (r1, c1) to location (r2, c2).
    */
    public void move(int r1, int c1, int r2, int c2);
}
