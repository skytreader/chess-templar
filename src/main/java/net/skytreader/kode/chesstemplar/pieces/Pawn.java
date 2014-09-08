package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn extends ChessPiece{

    private boolean hasMoved;
    
    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "PAWN";
        hasMoved = false;
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(b.getPieceAt(r, c))){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }

        HashSet<Point> moveSet = new HashSet<Point>();

        return moveSet;
    }
}
