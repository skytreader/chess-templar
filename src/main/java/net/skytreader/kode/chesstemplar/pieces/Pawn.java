package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn extends ChessPiece{

    private boolean hasMoved;
    
    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "PAWN";
        hasMoved = false;
    }

    @Override
    public Point[] getLegalMoves(int r, int c, Board b){
        return null;
    }
}
