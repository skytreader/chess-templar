package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Rook extends ChessPiece{
    
    public Rook(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "ROOK";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        return null;
    }
}
