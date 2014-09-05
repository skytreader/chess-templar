package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public abstract class ChessPiece{
    /**
    And then, "override" these properties as private. It is crucial that you
    override these---they are used for the equals and hashCode methods of
    this class.
    */
    protected String pieceName;
    protected boolean color;

    public static final boolean WHITE = true;
    /**
    Get all the legal moves of the piece, given its current position. If the
    piece at the given row and column is not equal to this ChessPiece, raise
    hell.

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

    /**
    Check if the piece at the given coordinates in the board is equal to this
    piece.
    */
    protected boolean isPieceAt(int r, int c, Board b){
        return b.getPieceAt(r, c).equals(this);
    }

    public boolean isWhite(){
        return this.color;
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        ChessPiece cp = (ChessPiece) o;
        return this.pieceName.equals(cp.pieceName) && this.color == cp.color;
    }

    public String getPieceName(){
        return pieceName;
    }

    @Override
    public int hashCode(){
        // Any better? Do with BLOCH's method?
        int discriminant = color ? 1 : 0;
        return pieceName.hashCode() + discriminant;
    }
}
