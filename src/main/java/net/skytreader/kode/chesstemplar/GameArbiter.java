package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Rook;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
The GameArbiter imposes the rules of Chess. An arbiter is tied to a particular
game. You can't make an Arbiter judge on multiple boards at a time!

By design, the GameArbiter is the only class that can interact with a Board
instance.

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

    private boolean lastMoveWhite;

    // Use these for comparisons
    private final King WHITE_KING = new King(true);
    private final King BLACK_KING = new King(false);
    private final Rook WHITE_ROOK = new Rook(true);
    private final Rook BLACK_ROOK = new Rook(false);

    public GameArbiter(Board b){
        board = b;
        whiteKingMoved = false;
        whiteKingsideRookMoved = false;
        whiteQueensideRookMoved = false;
        blackKingMoved = false;
        blackKingsideRookMoved = false;
        blackQueensideRookMoved = false;
        lastMoveWhite = false;
    }

    public boolean canWhiteKingCastle(){
        // FIXME Of course this is not yet all!
        return (!whiteKingMoved && !whiteKingsideRookMoved) ||
          (!whiteKingMoved && !whiteQueensideRookMoved);
    }

    public boolean canBlackKingCastle(){
        return (!blackKingMoved && !blackKingsideRookMoved) ||
          (!blackKingMoved && !blackQueensideRookMoved);
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

    The legality of the move takes into consideration whose turn is it to move.
    That is, you can't call requestMove two consecutive times on an r1, c1 tile
    holding a piece with the same color.

    To describe a castle move, move the King to it's terminal position once the
    move is castle is done.

    @param b
    @param r1
    @param c1
    @param r2
    @param c2
    @return true if the move described is possible and legal and has been enacted
      successfully on the given Board.
    */
    public boolean requestMove(Board b, int r1, int c1, int r2, int c2){
        boolean isMoveDone = false;

        // The move has been done if, after this call, (r2, c2) contains the piece
        // previously at (r1, c1).
        ChessPiece cp1 = b.getPieceAt(r1, c1);

        if((cp1.isWhite() && lastMoveWhite) || (!cp1.isWhite() &&
          !lastMoveWhite)){
            return false;
        }

        // Piece checks
        if(cp1 == null){
            return false;
        } else if(cp1.equals(WHITE_KING)){
            whiteKingMoved = true;
        } else if(cp1.equals(BLACK_KING)){
            blackKingMoved = true;
        } else if(cp1.equals(WHITE_ROOK) && r1 == 7 && c1 == 7){
            whiteKingsideRookMoved = true;
        } else if(cp1.equals(WHITE_ROOK) && r1 == 7 && c1 == 0){
            whiteQueensideRookMoved = true;
        } else if(cp1.equals(BLACK_ROOK) && r1 == 0 && c1 == 7){
            blackKingsideRookMoved = true;
        } else if(cp1.equals(BLACK_ROOK) && r1 == 0 && c1 == 0){
            blackQueensideRookMoved = true;
        }

        b.move(r1, c1, r2, c2);
        ChessPiece cp2 = b.getPieceAt(r2, c2);
        ChessPiece shouldBeNull = b.getPieceAt(r1, c1);

        isMoveDone = cp1.equals(cp2) && shouldBeNull == null;

        lastMoveWhite = isMoveDone && cp1.isWhite();

        return isMoveDone;
    }
}
