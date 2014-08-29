package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class King extends ChessPiece{
    
    public King(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "KING";
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
