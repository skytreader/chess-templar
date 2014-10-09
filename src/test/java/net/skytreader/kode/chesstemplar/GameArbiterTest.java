package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameArbiterTest{
    
    private GridBoard concreteBoard;
    private BlankBoard freeBoard;
    private GameArbiter rigidArbiter;
    private GameArbiter freeFormArbiter;

    @Before
    public void setUp(){
        concreteBoard = new GridBoard();
        freeBoard = new BlankBoard();
        rigidArbiter = new GameArbiter(concreteBoard);
        freeFormArbiter = new GameArbiter(freeBoard);
    }

    @Test
    public void testConcreteInitialState(){
        Assert.assertFalse(rigidArbiter.isEndgame());
        Assert.assertFalse(rigidArbiter.canWhiteKingCastle());
        Assert.assertFalse(rigidArbiter.canBlackKingCastle());
    }

    @Test
    public void testContrivedKingsideCastleScenarioWhite(){
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);
        Assert.assertTrue(rigidArbiter.canWhiteKingCastle());
    }
    
    @Test
    public void testKingsideCastleFilter() throws NotMeException{
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);

        King whiteKing = new King(true);
        Set<Point> withCastleMoves = whiteKing.getMoves(7, 4, concreteBoard);
        // extra move for castle
        withCastleMoves.add(new Point(7, 6));

        Set<Point> kingLegalMoves = rigidArbiter.legalMovesFilter(whiteKing,
          7, 4, concreteBoard);

        Assert.assertEquals(withCastleMoves, kingLegalMoves);
    }

    @Test
    public void testQueensideCastleFilter() throws NotMeException{
        concreteBoard.removePiece(7, 1);
        concreteBoard.removePiece(7, 2);
        concreteBoard.removePiece(7, 3);

        King whiteKing = new King(true);
        Set<Point> withCastleMoves = whiteKing.getMoves(7, 4, concreteBoard);
        // extra move for castle
        withCastleMoves.add(new Point(7, 2));

        Set<Point> kingLegalMoves = rigidArbiter.legalMovesFilter(whiteKing,
          7, 4, concreteBoard);

        Assert.assertEquals(withCastleMoves, kingLegalMoves);
    }

    @Test
    public void testContrivedQueensideCastleScenarioWhite(){
        concreteBoard.removePiece(7, 3);
        concreteBoard.removePiece(7, 2);
        concreteBoard.removePiece(7, 1);
        Assert.assertTrue(rigidArbiter.canWhiteKingCastle());
    }

    /**
    A test for legalMovesFilter where, while the King is in check, all moves
    that does not put the King to safety should be illegal.
    */
    @Test
    public void testWhiteKingProtectionPriority() throws NotMeException{
        // Expose both Kings.
        concreteBoard.removePiece(6, 4);
        concreteBoard.removePiece(1, 4);
        // Move black Queen to (1, 4) checking white King.
        concreteBoard.move(0, 3, 1, 4);
        
        /*
        White has only three legal moves now, all covering the King via the
        (6, 4) square:
            - Qe2
            - Be2
            - Ne2

        Fortunately that's just one unique set of move(s).
        */
        Set<Point> coverMove = new HashSet<Point>();
        coverMove.add(new Point(6, 4));
        // where all the valid defenders are located.
        Set<Point> validDefenders = new HashSet<Point>();
        validDefenders.add(new Point(7, 3)); // Queen @ d1
        validDefenders.add(new Point(7, 5)); // Bishop @ f1
        validDefenders.add(new Point(7, 6)); // Knight @ g1
 
        for(Point defenderLocation : validDefenders){
            ChessPiece defender = concreteBoard.getPieceAt(defenderLocation.x,
              defenderLocation.y);
            Set<Point> defenderMoves = rigidArbiter.legalMovesFilter(defender,
              defenderLocation.x, defenderLocation.y, concreteBoard);

            Assert.assertEquals(coverMove, defenderMoves);
        }

        Set<Point> emptySet = new HashSet<Point>();
        
        // Check the pawns
        for(int i = 0; i < 8; i++){
            if(i == 4){
                // Skip. This pawn was vacated above.
                continue;
            }
            ChessPiece pawn = concreteBoard.getPieceAt(6, i);
            Set<Point> pawnMoves = rigidArbiter.legalMovesFilter(pawn, 6, i,
              concreteBoard);

            Assert.assertEquals(emptySet, pawnMoves);
        }

        // Check the other pieces
        for(int i = 0; i < 8; i++){
            if(validDefenders.contains(new Point(7, i))){
                continue;
            }
            ChessPiece p = concreteBoard.getPieceAt(7, i);
            Set<Point> pawnMoves = rigidArbiter.legalMovesFilter(p, 7, i,
              concreteBoard);
            Set<Point> pieceMoves = p.getMoves(7, i, concreteBoard);

            Assert.assertEquals(emptySet, pieceMoves);
        }
    }
}
