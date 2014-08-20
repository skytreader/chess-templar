package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

public interface ChessPiece{
    /**
    Get all the legal moves of the piece, given its current position.

    @param r
        An integer describing the current row of the piece. Range 0-7. In
        algebraic notation, this is the <i>rank</i>.
    @param c
        An integer describing the current column of the piece. Range 0-7. In
        algebraic notation this is the <i>file</i>.
    */
    public Point[] getLegalMoves(int r, int c);
}
