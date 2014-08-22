package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public abstract class ChessPiece{
    /**
    Get all the legal moves of the piece, given its current position.

    @param r
        An integer describing the current row of the piece. Range 0-7. In
        algebraic notation, this is the <i>rank</i>.
    @param c
        An integer describing the current column of the piece. Range 0-7. In
        algebraic notation this is the <i>file</i>.
    @param b
        The current state of the board.
    @return
        An array of Point objects containing all the legal moves for this piece.
        The x attribute corresponds to the row while the y attribute corresponds
        to the column.
    */
    public abstract Point[] getLegalMoves(int r, int c, Board b);

    protected boolean isPieceAt(int r, int c, Board b){
        return b.getPieceAt(r, c).equals(this);
    }

    public abstract boolean isWhite();
}
