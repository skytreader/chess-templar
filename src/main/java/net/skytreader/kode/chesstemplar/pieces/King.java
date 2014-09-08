package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class King extends ChessPiece{
    
    public King(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "KING";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        return null;
    }
}
