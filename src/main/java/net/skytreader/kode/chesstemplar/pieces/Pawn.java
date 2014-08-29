package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn extends ChessPiece{
    
    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "PAWN";
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
