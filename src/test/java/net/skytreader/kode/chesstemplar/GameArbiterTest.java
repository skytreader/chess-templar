package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Rook;

import java.util.Arrays;

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
    public void testGetLastMove(){
        Point[] expected0 = new Point[2];
        Assert.assertTrue(Arrays.equals(expected0, rigidArbiter.getLastMove()));
        Point from1 = new Point(6, 0);
        Point to1 = new Point(4, 0);
        Point[] expected1 = {from1, to1};
        Assert.assertTrue(rigidArbiter.requestMove(6, 0, 4, 0));
        Assert.assertTrue(Arrays.equals(expected1, rigidArbiter.getLastMove()));

        Point from2 = new Point(1, 0);
        Point to2 = new Point(3, 0);
        Point[] expected2 = {from2, to2};
        rigidArbiter.requestMove(1, 0, 3, 0);
        Assert.assertTrue(Arrays.equals(expected2, rigidArbiter.getLastMove()));
    }
    
    /**
    Test that invalid requests should not be counted as "last move". This tests
    the invalid scenario where black moves first.
    */
    @Test
    public void testInvalidLastMovesBlackFirst(){
        // Move black first
        Assert.assertFalse(rigidArbiter.requestMove(1, 0, 3, 0));
        Assert.assertTrue(Arrays.equals(new Point[2], rigidArbiter.getLastMove()));
    }
    
    /**
    Test that invalid requests should not be counted as "last move". This tests
    the invalid scenario where a move is requested from a blank square.
    */
    public void testInvalidLastMovesBlankSquare(){
        Assert.assertFalse(rigidArbiter.requestMove(4, 4, 4, 5));
        Assert.assertTrue(Arrays.equals(new Point[2], rigidArbiter.getLastMove()));
    }

    /**
    Test conditions of the initial state of the game.
    */
    @Test
    public void testConcreteInitialState(){
        Assert.assertFalse(rigidArbiter.isEndgame());
        Assert.assertFalse(rigidArbiter.canWhiteKingCastle());
        Assert.assertFalse(rigidArbiter.canBlackKingCastle());

        // Test that, on its initial state, only the white side has legal moves
        // Moving a black pawn should not be allowed
        Assert.assertFalse(rigidArbiter.requestMove(1, 0, 3, 0));
        Assert.assertTrue(rigidArbiter.requestMove(6, 0, 4, 0));

        // Kings are not yet checked
        Assert.assertFalse(rigidArbiter.isWhiteKingChecked());
        Assert.assertFalse(rigidArbiter.isBlackKingChecked());
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
    
    /**
    Test that the right of castling is invalidated when the Kingside Rook moves.
    */
    @Test
    public void testWhiteKingsideRookNoCastle(){
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);
        
        // Move the white Kingside Rook
        Assert.assertTrue(rigidArbiter.requestMove(7, 7, 7, 6));
        // Move a black pawn
        Assert.assertTrue(rigidArbiter.requestMove(1, 0, 2, 0));
        // Move back the white Kingside Rook
        Assert.assertTrue(rigidArbiter.requestMove(7, 6, 7, 7));
        
        Assert.assertFalse(rigidArbiter.canWhiteKingCastle());
    }

    /**
    Test that the right of castling is invalidated when the King moves.
    */
    @Test
    public void testWhiteKingNoCastle(){
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);

        // Move the white King NOT CASTLE MOVE!
        Assert.assertTrue(rigidArbiter.requestMove(7, 4, 7, 5));
        Assert.assertTrue(rigidArbiter.requestMove(1, 0, 2, 0));
        Assert.assertTrue(rigidArbiter.requestMove(7, 5, 7, 4)); 

        Assert.assertFalse(rigidArbiter.canWhiteKingCastle());
    }

    @Test
    public void testContrivedQueensideCastleScenarioWhite(){
        concreteBoard.removePiece(7, 3);
        concreteBoard.removePiece(7, 2);
        concreteBoard.removePiece(7, 1);
        Assert.assertTrue(rigidArbiter.canWhiteKingCastle());
    }

    @Test
    public void testWhiteKingChecked(){
        // Expose both Kings.
        concreteBoard.removePiece(6, 4);
        concreteBoard.removePiece(1, 4);
        // Move black Queen to (1, 4) checking white King.
        concreteBoard.move(0, 3, 1, 4);
        Assert.assertTrue(rigidArbiter.isWhiteKingChecked());
    }

    @Test
    public void testBlackKingChecked(){
        // Expose both Kings.
        concreteBoard.removePiece(6, 4);
        concreteBoard.removePiece(1, 4);
        // Move white Queen to (6, 4) checking the black King
        concreteBoard.move(7, 3, 6, 4);
        Assert.assertTrue(rigidArbiter.isBlackKingChecked());
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

    /**
    Test that on request of move, the square (r1, c1) is indeed vacated and that
    the piece is now in the square (r2, c2).
    */
    @Test
    public void testActualMove(){
        Assert.assertTrue(concreteBoard.getPieceAt(5, 4) == null);
        ChessPiece forMoving = concreteBoard.getPieceAt(6, 4);
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
        Assert.assertTrue(concreteBoard.getPieceAt(6, 4) == null);
        Assert.assertTrue(concreteBoard.getPieceAt(5, 4).equals(forMoving));
    }

    @Test
    public void testNoConsecutiveWhite(){
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
        Assert.assertFalse(rigidArbiter.requestMove(5, 4, 4, 4));
    }

    @Test
    public void testNoConsecutiveBlack(){
        // just move a white piece first
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
        Assert.assertTrue(rigidArbiter.requestMove(1, 4, 2, 4));
        Assert.assertFalse(rigidArbiter.requestMove(2, 4, 3, 4));
    }
    
    /**
    Moves requested from a blank square should return false.
    */
    @Test
    public void testBlankSquareMove(){
        Assert.assertFalse(rigidArbiter.requestMove(4, 4, 5, 4));
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
    }

    @Test
    public void testWhiteKingsideCastleRequest(){
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);
        Assert.assertTrue(rigidArbiter.requestMove(7, 4, 7, 6));

        King whiteKing = new King(true);
        Rook whiteRook = new Rook(true);

        Assert.assertEquals(concreteBoard.getPieceAt(7, 6), whiteKing);
        Assert.assertEquals(concreteBoard.getPieceAt(7, 5), whiteRook);
    }

    @Test
    public void testWhiteQueensideCastleRequest(){
        // TODO
    }

    @Test
    public void testBlackKingsideCastleRequest(){
        // TODO
    }

    @Test
    public void testBlackQueensideCastleRequest(){
        // TODO
    }
}
