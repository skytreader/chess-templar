package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Bishop extends ChessPiece{
    
    public Bishop(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "BISHOP";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(indicatedPiece)){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }
        HashSet<Point> legalMoves = new HashSet<Point>();
        return legalMoves;
    }
}
