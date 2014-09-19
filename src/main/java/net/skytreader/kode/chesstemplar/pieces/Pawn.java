package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

public class Pawn extends ChessPiece{

    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "PAWN";
    }

    @Override
    public Set<Point> getLegalMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(indicatedPiece)){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }

        HashSet<Point> moveSet = new HashSet<Point>();
        // Always add the move forward scenario
        if(this.color){
            moveSet.add(new Point(r - 1, c));
        } else{
            moveSet.add(new Point(r + 1, c));
        }

        // If you are white and unmoved, you are at the second-to-the-last row
        if(this.color && r == 6){
            // Add two blocks ahead.
            moveSet.add(new Point(r - 2, c));
        } else if(!this.color && r == 1){
            moveSet.add(new Point(r + 2, c));
        }

        // Check if you can capture
        // TODO En passant capture
        if(this.color){
            ChessPiece upperLeft = b.getPieceAt(r - 1, c - 1);
            ChessPiece upperRight = b.getPieceAt(r - 1, c + 1);
            if(upperLeft != null && !upperLeft.isWhite()){
                moveSet.add(new Point(r - 1, c - 1));
            }

            if(upperRight != null && !upperRight.isWhite()){
                moveSet.add(new Point(r - 1, c + 1));
            }
        } else {
            ChessPiece upperLeft = b.getPieceAt(r + 1, c - 1);
            ChessPiece upperRight = b.getPieceAt(r + 1, c + 1);

            if(upperLeft != null && upperLeft.isWhite()){
                moveSet.add(new Point(r + 1, c - 1));
            }

            if(upperRight != null && upperRight.isWhite()){
                moveSet.add(new Point(r + 1, c + 1));
            }
        }

        return moveSet;
    }
}
