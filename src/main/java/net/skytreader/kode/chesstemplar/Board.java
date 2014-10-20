package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
This interface abstracts Chess boards.

Chess boards are containers of ChessPieces, keeping track of their position in
the board and just that. Chess rules are not enforced by Chess boards. They
are just containers for ChessPieces.

@author Chad Estioco
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
    Get a list of positions where there are pieces on the board.
    */
    public Set<Point> getPiecePositions();

    /**
    Move the piece at the location (r1, c1) to location (r2, c2).
    */
    public void move(int r1, int c1, int r2, int c2);

    /**
    Remove a piece from the board. Use this for captures. If there is no piece
    at the indicated location, then nothing happens.
    */
    public void removePiece(int r, int c);

    /**
    Return a String representation of a Board.
    */
    @Override
    public String toString();
}
