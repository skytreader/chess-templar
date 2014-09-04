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
    */
    public ChessPiece getPieceAt(int r, int c);
}
