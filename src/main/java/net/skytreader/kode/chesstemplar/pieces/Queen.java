package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

public class Queen extends ChessPiece{
    
    public Queen(boolean isWhite){
        this.color = isWhite;
        this.pieceName = "QUEEN";
    }

    @Override
    public Set<Point> getMoves(int r, int c, Board b) throws NotMeException{
        ChessPiece indicatedPiece = b.getPieceAt(r, c);
        if(!this.equals(indicatedPiece)){
            throw new NotMeException("Attempting to move piece " + indicatedPiece.toString()
              + " using " + this.toString());
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
            } else if(p != null && p.isWhite() == indicatedPiece.isWhite()){
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
            } else if(p != null && p.isWhite() == indicatedPiece.isWhite()){
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
            } else if(p != null && p.isWhite() == indicatedPiece.isWhite()){
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
            } else if(p != null && p.isWhite() == indicatedPiece.isWhite()){
                break;
            }
        }

        // (r+1, c+1);
        for(int row = r + 1, col = c + 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row++, col++){
            ChessPiece currentSquare = b.getPieceAt(row, col);
            if(currentSquare != null){
                // Capture condition
                if(currentSquare.isWhite() != this.isWhite()){
                    legalMoves.add(new Point(row, col));
                }
                break;
            }
            legalMoves.add(new Point(row, col));
        } 

        // (r-1, c-1)
        for(int row = r - 1, col = c - 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row--, col--){
            ChessPiece currentSquare = b.getPieceAt(row, col);
            if(currentSquare != null){
                // Capture condition
                if(currentSquare.isWhite() != this.isWhite()){
                    legalMoves.add(new Point(row, col));
                }
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        //(r + 1, c - i)
        for(int row = r + 1, col = c -1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row++, col--){
            ChessPiece currentSquare = b.getPieceAt(row, col);
            if(currentSquare != null){
                // Capture condition
                if(currentSquare.isWhite() != this.isWhite()){
                    legalMoves.add(new Point(row, col));
                }
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        // (r - 1, c + 1)
        for(int row = r - 1, col = c + 1; (0 <= row && row < 8 && 0 <= col && col < 8);
          row--, col++){
            ChessPiece currentSquare = b.getPieceAt(row, col);
            if(currentSquare != null){
                // Capture condition
                if(currentSquare.isWhite() != this.isWhite()){
                    legalMoves.add(new Point(row, col));
                }
                break;
            }
            legalMoves.add(new Point(row, col));
        }

        return legalMoves;
    }
}
