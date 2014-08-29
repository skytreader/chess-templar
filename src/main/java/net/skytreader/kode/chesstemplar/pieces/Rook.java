package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Rook extends ChessPiece{
    
    public Rook(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "ROOK";
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
