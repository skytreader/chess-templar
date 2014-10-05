package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
The GameArbiter imposes the rules of Chess. An arbiter is tied to a particular
game. You can't make an Arbiter judge on multiple boards at a time!

@author Chad Estioco
*/
public class GameArbiter{
    
    private Board board;
    private boolean whiteKingMoved;
    private boolean whiteKingsideRookMoved;
    private boolean whiteQueensideRookMoved;
    private boolean blackKingMoved;
    private boolean blackKingsideRookMoved;
    private boolean blackQueensideRookMoved;

    public GameArbiter(Board b){
        board = b;
        whiteKingMoved = false;
        whiteKingsideRookMoved = false;
        whiteQueensideRookMoved = false;
        blackKingMoved = false;
        blackKingsideRookMoved = false;
        blackQueensideRookMoved = false;
    }

    public boolean canWhiteKingCastle(){
        return false;
    }

    public boolean canBlackKingCastle(){
        return false;
    }

    public boolean isWhiteKingChecked(){
        return false;
    }

    public boolean isBlackKingChecked(){
        return false;
    }

    public boolean isEndgame(){
        return false;
    }

    /**
    Edits the moveset returned by the ChessPiece to take Chess rules in
    consideration.

    Among the edits involved are removing moves that will expose the King to
    a check and, in the case of the King, add the possibility of castles.
    */
    public Set<Point> legalMovesFilter(ChessPiece cp, int r, int c, Board b) throws NotMeException{
        Set<Point> pieceMoves = cp.getMoves(r, c, b);

        return pieceMoves;
    }
    
    /**
    Checks if the described move is possible and legal and enacts it on the Board
    if it is so. The move is described as (r1, c1) being the initial square and
    (r2, c2) being the terminal square. This method returns true if the move
    described is possible and legal and has been enacted succesfully on the given
    Board.

    @param b
    @param r1
    @param c1
    @param r2
    @param c2
    @return true if the move described is possible and legal and has been enacted
      successfully on the given Board.
    */
    public boolean requestMove(Board b, int r1, int c1, int r2, int c2){
        return false;
    }
}
