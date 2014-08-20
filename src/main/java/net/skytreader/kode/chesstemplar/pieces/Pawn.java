package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn implements ChessPiece{
    
    private boolean isWhite;

    public Pawn(boolean isWhite){
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhite(){
        return isWhite;
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
