package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

public class Rook extends ChessPiece{

    public static final String PIECE_NAME = "ROOK";
    
    public Rook(boolean isWhite){
        this.color = isWhite;
        this.pieceName = Rook.PIECE_NAME;
    }

    @Override
    public Set<Point> getMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(b.getPieceAt(r, c))){
            throw new NotMeException("Attempting to move piece " +
              indicatedPiece.toString() + " using " + this.toString());
        }
        HashSet<Point> legalMoves = new HashSet<Point>();
        
        // first go backward on rows
        for(int i = r - 1; i >= 0; i--){
            ChessPiece p = b.getPieceAt(i, c);

            if(p == null){
                legalMoves.add(new Point(i, c));
            }else if(p != null && (p.isWhite() ^ indicatedPiece.isWhite())){
                legalMoves.add(new Point(i, c));
                break;
            } else if(p != null && (p.isWhite() == indicatedPiece.isWhite())){
                break;
            }
        }

        // go backward on cols
        for(int i = c - 1; i >= 0; i--){
            ChessPiece p = b.getPieceAt(r, i);

            if(p == null){
                legalMoves.add(new Point(r, i));
            } else if(p != null && (p.isWhite() ^ indicatedPiece.isWhite())){
                legalMoves.add(new Point(r, i));
                break;
            } else if(p != null && (p.isWhite() == indicatedPiece.isWhite())){
                break;
            }
        }

        // go forward on rows
        for(int i = r + 1; i < 8; i++){
            ChessPiece p = b.getPieceAt(i, c);

            if(p == null){
                legalMoves.add(new Point(i, c));
            } else if(p != null && (p.isWhite() ^ indicatedPiece.isWhite())){
                legalMoves.add(new Point(i, c));
                break;
            } else if(p != null && (p.isWhite() == indicatedPiece.isWhite())){
                break;
            }
        }

        // go forward on cols
        for(int i = c + 1; i < 8; i++){
            ChessPiece p = b.getPieceAt(r, i);

            if(p == null){
                legalMoves.add(new Point(r, i));
            } else if(p != null && (p.isWhite() ^ indicatedPiece.isWhite())){
                legalMoves.add(new Point(r, i));
                break;
            } else if(p != null && (p.isWhite() == indicatedPiece.isWhite())){
                break;
            }
        }

        return legalMoves;
    }
}
