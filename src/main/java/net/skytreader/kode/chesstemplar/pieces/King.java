package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

public class King extends ChessPiece{
    
    public King(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "KING";
    }

    private boolean movesetFilter(Point p){
        return 0 <= p.x && p.x < 8 && 0<= p.y && p.y < 8;
    }

    /**
    The King's moveset does not include the possibility of castling since the
    King piece should be oblivious to the conditions of castling.

    @param r
    @param c
    @param b
    */
    @Override
    public Set<Point> getMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(indicatedPiece)){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }
        HashSet<Point> moveSet = new HashSet<Point>();

        Point[] allAround = {new Point(r - 1, c - 1), new Point(r - 1, c),
          new Point(r - 1, c + 1), new Point(r, c - 1), new Point(r, c + 1),
          new Point(r + 1, c - 1), new Point(r + 1, c), new Point(r + 1, c + 1)};

        for(Point p : allAround){
            ChessPiece thisPiece = b.getPieceAt(p.x, p.y);
            if((thisPiece == null || thisPiece.isWhite() ^ this.isWhite())
              && movesetFilter(p)){
                moveSet.add(p);
            }
        }
        
        return moveSet;
    }
}
