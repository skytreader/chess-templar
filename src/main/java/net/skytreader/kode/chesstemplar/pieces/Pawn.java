package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn extends ChessPiece{

    private boolean hasMoved;
    
    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "PAWN";
        hasMoved = false;
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        return null;
    }
}
