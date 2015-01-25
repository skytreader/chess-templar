package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

public class Pawn extends ChessPiece{
    
    public static final String PIECE_NAME = "PAWN";

    public static final int WHITE_INITIAL_ROW = 6;
    public static final int WHITE_SPECIAL_MOVE_ROW = 4;
    public static final int BLACK_INITIAL_ROW = 1;
    public static final int BLACK_SPECIAL_MOVE_ROW = 3;

    public Pawn(boolean isWhite){
        this.color = isWhite;
        this.pieceName = Pawn.PIECE_NAME;
    }

    @Override
    public Set<Point> getMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(indicatedPiece)){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }

        HashSet<Point> moveSet = new HashSet<Point>();
        boolean isMoveAdded = false;
        // Always add the move forward scenario if there is nothing in front.
        if(this.color && b.getPieceAt(r - 1, c) == null){
            moveSet.add(new Point(r - 1, c));
            isMoveAdded = true;
        } else if(!this.color && b.getPieceAt(r + 1, c) == null){
            moveSet.add(new Point(r + 1, c));
            isMoveAdded = true;
        }

        // If you are white and unmoved, you are at the second-to-the-last row
        if(this.color && r == Pawn.WHITE_INITIAL_ROW && b.getPieceAt(Pawn.WHITE_SPECIAL_MOVE_ROW, c) == null){
            // Add two blocks ahead.
            moveSet.add(new Point(r - 2, c));
        } else if(!this.color && r == Pawn.BLACK_INITIAL_ROW && b.getPieceAt(Pawn.BLACK_SPECIAL_MOVE_ROW, c) == null){
            moveSet.add(new Point(r + 2, c));
        }

        // Check if you can capture
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
