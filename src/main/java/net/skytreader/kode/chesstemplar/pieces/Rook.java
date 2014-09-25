package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Rook extends ChessPiece{
    
    public Rook(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "ROOK";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(b.getPieceAt(r, c))){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }
        HashSet<Point> legalMoves = new HashSet<Point>();

        for(int i = 0; i < 8; i++){
            if(i != r){
                legalMoves.add(new Point(i, c));
            }

            if(i != c){
                legalMoves.add(new Point(r, i));
            }
        }
        return legalMoves;
    }
}
