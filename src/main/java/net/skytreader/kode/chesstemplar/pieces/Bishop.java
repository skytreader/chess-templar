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

        /*
        So, in computer 2D array convention, the directions of a Bishop's
        movements are (r+1, c+1), (r-1, c-1), (r+1, c-1), (r-1, c+1)
        */

        // (r+1, c+1);
        for(int row = r + 1, col = c + 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row++, col++){
            if(b.getPieceAt(row, col) == null){
                break;
            }
            legalMoves.add(new Point(row, col));
        } 

        // (r-1, c-1)
        for(int row = r - 1, col = c - 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row--, col--){
            if(b.getPieceAt(row, col) == null){
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        //(r + 1, c - i)
        for(int row = r + 1, col = c -1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row++, col--){
            if(b.getPieceAt(row, col) == null){
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        // (r - 1, c + 1)
        for(int row = r - 1, col = c + 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row--, col++){
            if(b.getPieceAt(row, col) == null){
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        return legalMoves;
    }
}
