package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Knight extends ChessPiece{
    
    public Knight(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "KNIGHT";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b){
        return null;
    }
}
