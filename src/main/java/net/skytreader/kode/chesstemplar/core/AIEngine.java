package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GameArbiter;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

public abstract class AIEngine{
    
    private GameArbiter arbiter;

    public AIEngine(GameArbiter arbiter){
        this.arbiter = arbiter;
    }

    protected Set<Point[]> getAllLegalMoves(){
        Set<Point[]> legalMoves = new HashSet<Point[]>();
        Board b = arbiter.getBoard();
        /*
        TODO I'm doing this pattern a lot:

        - get all the piece positions in the board
        - for each piece position in the board, get their moves; this involves
          repeated invocations of their position in the board.

        Maybe I should refactor.

        Moreover, this always needs me to catch NotMeExceptions even when I'm so
        sure that it isn't going to happen this time.
        */
        Set<Point> pieces = b.getPiecePositions();

        try{
            for(Point piecePos : pieces){
                ChessPiece cp = b.getPieceAt(piecePos.x, piecePos.y);
                Set<Point> pieceMoves = cp.getMoves(piecePos.x, piecePos.y, b);

                for(Point terminalSquare : pieceMoves){
                    if(arbiter.requestMove(piecePos.x, piecePos.y, terminalSquare.x,
                      terminalSquare.y)){
                        Point[] pa = {piecePos, terminalSquare};
                        legalMoves.add(pa);
                    }
                }
            }
        } catch(NotMeException nme){
            nme.printStackTrace();
        }

        return legalMoves;
    }

    public abstract Point[] getMove();
}
