package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Bishop extends ChessPiece{
    
    public Bishop(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "BISHOP";
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
