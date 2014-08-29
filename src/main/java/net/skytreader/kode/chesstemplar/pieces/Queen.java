package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Queen extends ChessPiece{
    
    public Queen(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "QUEEN";
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
