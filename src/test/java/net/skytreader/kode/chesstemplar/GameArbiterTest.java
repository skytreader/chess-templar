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
import org.junit.Ignore;
import org.junit.Test;

public class GameArbiterTest{
    
    private GridBoard concreteBoard;
    private BlankBoard freeBoard;
    private GameArbiter rigidArbiter;
    private GameArbiter freeFormArbiter;

    private void executeMoveSequence(Point[] moveSeqSrc, Point[] moveSeqDst){
        Assert.assertEquals(moveSeqSrc.length, moveSeqDst.length);
        
        for(int i = 0; i < moveSeqSrc.length; i++){
            Assert.assertTrue(rigidArbiter.requestMove(moveSeqSrc[i].x,
              moveSeqSrc[i].y, moveSeqDst[i].x, moveSeqDst[i].y));
        }
    }

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
        Assert.assertFalse(rigidArbiter.requestMove(1, 0, 3, 0));
        Assert.assertTrue(Arrays.equals(new Point[2], rigidArbiter.getLastMove()));
    }

    /**
    Test conditions of the initial state of the game.
    */
    @Test
    public void testConcreteInitialState(){
        Assert.assertFalse(rigidArbiter.isEndgame());

        // Test that, on its initial state, only the white side has legal moves
        // Moving a black pawn should not be allowed
        Assert.assertFalse(rigidArbiter.requestMove(1, 0, 3, 0));
        Assert.assertTrue(rigidArbiter.requestMove(6, 0, 4, 0));

        // Kings are not yet checked
        Assert.assertFalse(rigidArbiter.isWhiteKingChecked());
        Assert.assertFalse(rigidArbiter.isBlackKingChecked());
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
          7, 4);

        Assert.assertEquals(withCastleMoves, kingLegalMoves);
    }
    
    /**
    Test that the right of castling is invalidated when the Kingside Rook moves.

    1 h4 a6
    2 Rh3 b6
    3 Rh1 c6
    4 g3 d6
    5 Bg2 e6
    6 Nf3 f6
    */
    @Test
    public void testWhiteKingsideRookNoCastle(){
        Point[] moveSeqSrc = {new Point(6, 7), new Point(1, 0), new Point(7, 7),
          new Point(1, 1), new Point(5, 7), new Point(1, 2), new Point(6, 6),
          new Point(1, 3), new Point(7, 5), new Point(1, 4), new Point(7, 6),
          new Point(1, 5)};
        Point[] moveSeqDst = {new Point(4, 7), new Point(2, 0), new Point(5, 7),
          new Point(2, 1), new Point(7, 7), new Point(2, 2), new Point(5, 6),
          new Point(2, 3), new Point(6, 6), new Point(2, 4), new Point(5, 5),
          new Point(2, 5)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        
        // Request for a king side castle
        Assert.assertFalse(rigidArbiter.requestMove(7, 4, 7, 6));
    }

    /**
    Test that the right of castling is invalidated when the King moves.

    1 e4 e5
    2 Nf3 d6
    3 Bb5+ c6
    4 Kf1 f6
    5 Ke1 g6
    */
    @Test
    public void testWhiteKingNoCastle(){
        Point[] moveSeqSrc = {new Point(6, 4), new Point(1, 4), new Point(7, 6),
          new Point(1, 3), new Point(7, 5), new Point(1, 2), new Point(7, 4),
          new Point(1, 5), new Point(7, 5), new Point(1, 6)};
        Point[] moveSeqDst = {new Point(4, 4), new Point(3, 4), new Point(5, 5),
          new Point(2, 3), new Point(3, 1), new Point(2, 2), new Point(7, 5),
          new Point(2, 5), new Point(7, 4), new Point(2, 6)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);

        Assert.assertFalse(rigidArbiter.requestMove(7, 4, 7, 6));
    }

    /**
    1 d4 e5
    2 e3 Bb4+
    */
    @Test
    public void testWhiteKingChecked(){
        Point[] moveSeqSrc = {new Point(6, 3), new Point(1, 4), new Point(6, 4),
          new Point(0, 5)};
        Point[] moveSeqDst = {new Point(4, 3), new Point(3, 4), new Point(5, 4),
          new Point(4, 1)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        Assert.assertTrue(rigidArbiter.isWhiteKingChecked());
    }

    /**
    Ruy-Lopez!
    1 e4 e5
    2 Nf3 d6
    3 Bb5+
    */
    @Test
    public void testBlackKingChecked(){
        Point[] moveSeqSrc = {new Point(6, 4), new Point(1, 4), new Point(7, 6),
          new Point(1, 3), new Point(7, 5)};
        Point[] moveSeqDst = {new Point(4, 4), new Point(3, 4), new Point(5, 5),
          new Point(2, 3), new Point(3, 1)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        Assert.assertTrue(rigidArbiter.isBlackKingChecked());
    }

    /**
    A test for legalMovesFilter where, while the King is in check, all moves
    that does not put the King to safety should be illegal.

    King's Pawn
    1 e4 e5
    2 Nf3 d5
    3 exd5 c6
    4 Nxe5 f6
    5 Nxc6 Qd7+
    */
    @Test
    public void testWhiteKingProtectionPriority() throws NotMeException{
        System.out.println("================testWhiteKingProtectionPriority====");
        Point[] moveSeqSrc = {new Point(6, 4), new Point(1, 4), new Point(7, 6),
          new Point(1, 3), new Point(4, 4), new Point(1, 2), new Point(5, 5),
          new Point(1, 5), new Point(3, 4), new Point(0, 3)};
        Point[] moveSeqDst = {new Point(4, 4), new Point(3, 4), new Point(5, 5),
          new Point(3, 3), new Point(3, 3), new Point(2, 2), new Point(3, 4),
          new Point(2, 5), new Point(2, 2), new Point(1, 4)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        
        /*
        White has only four legal moves now, two covering the King via the
        (6, 4) e2 square:
            - Qe2
            - Be2
        
        and two using the Knight at (2, 2) c6:
            - Ne5
            - Nxe7
        */
        Set<Point> coverMove64 = new HashSet<Point>();
        coverMove64.add(new Point(6, 4));
        // where all the valid defenders are located.
        Set<Point> valid64Defenders = new HashSet<Point>();
        valid64Defenders.add(new Point(7, 3)); // Queen @ d1
        valid64Defenders.add(new Point(7, 5)); // Bishop @ f1
 
        for(Point defenderLocation : valid64Defenders){
            ChessPiece defender = concreteBoard.getPieceAt(defenderLocation.x,
              defenderLocation.y);
            Set<Point> defenderMoves = rigidArbiter.legalMovesFilter(defender,
              defenderLocation.x, defenderLocation.y);

            Assert.assertEquals(coverMove64, defenderMoves);
        }

        // The knight defense
        Set<Point> coverMove34 = new HashSet<Point>();
        coverMove34.add(new Point(3, 4));
        coverMove34.add(new Point(1, 4));
        ChessPiece defender = concreteBoard.getPieceAt(2, 2);
        Set<Point> defenderMoves = rigidArbiter.legalMovesFilter(defender, 2, 2);

        Assert.assertEquals(coverMove34, defenderMoves);

        Set<Point> emptySet = new HashSet<Point>();
        
        // Check the pawns
        for(int i = 0; i < 8; i++){
            if(i == 4){
                // Skip. This pawn was vacated above.
                continue;
            }
            ChessPiece pawn = concreteBoard.getPieceAt(6, i);
            Set<Point> pawnMoves = rigidArbiter.legalMovesFilter(pawn, 6, i);

            Assert.assertEquals(emptySet, pawnMoves);
        }

        // Check the other pieces
        for(int i = 0; i < 8; i++){
            // i == 6 the Knight that moved
            if(valid64Defenders.contains(new Point(7, i)) || i == 6){
                continue;
            }
            ChessPiece p = concreteBoard.getPieceAt(7, i);
            System.out.println("testWhiteKingProtectionPriority checking " + p + " at " + 7 + " " + i);
            Set<Point> pieceMoves = rigidArbiter.legalMovesFilter(p, 7, i);

            System.out.println("testWhiteKingProtectionPriority Asserting empty set...");
            Assert.assertEquals(emptySet, pieceMoves);
            System.out.println("testWhiteKingProtectionPriority Empty set indeed.");
        }
        System.out.println("=======end testWhiteKingProtectionPriority=======");
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
    
    /**
    Test that you can't just request pieces to be moved plain anywhere. The move
    should be in the pieces legal moves.
    */
    @Test
    public void testInvalidMoveRequest(){
        Assert.assertFalse(rigidArbiter.requestMove(7, 1, 5, 1));
        Assert.assertTrue(Arrays.equals(new Point[2], rigidArbiter.getLastMove()));
    }

    @Test
    public void testNoConsecutiveWhite(){
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
        Assert.assertFalse(rigidArbiter.requestMove(5, 4, 4, 4));
    }

    //@Test
    public void testNoConsecutiveBlack(){
        // just move a white piece first
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
        Assert.assertTrue(rigidArbiter.requestMove(1, 4, 2, 4));
        Assert.assertFalse(rigidArbiter.requestMove(2, 4, 3, 4));
    }
    
    /**
    Moves requested from a blank square should return false. Also tests that
    request to move from a blank square should not be taken as the game's
    most recent move.
    */
    @Test
    public void testBlankSquareMove(){
        Assert.assertFalse(rigidArbiter.requestMove(4, 4, 5, 4));
        Assert.assertTrue(Arrays.equals(new Point[2], rigidArbiter.getLastMove()));
        Assert.assertTrue(rigidArbiter.requestMove(6, 4, 5, 4));
    }
    
    /**
    1 e4 e5
    2 Nf3 d6
    3 Bb5+ c6
    */
    @Test
    public void testWhiteKingsideCastleRequest(){
        Point[] moveSeqSrc = {new Point(6, 4), new Point(1, 4), new Point(7, 6),
          new Point(1, 3), new Point(7, 5), new Point(1, 2)};
        Point[] moveSeqDst = {new Point(4, 4), new Point(3, 4), new Point(5, 5),
          new Point(2, 3), new Point(3, 1), new Point(2, 2)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        Assert.assertTrue(rigidArbiter.requestMove(7, 4, 7, 6));

        King whiteKing = new King(true);
        Rook whiteRook = new Rook(true);

        Assert.assertEquals(concreteBoard.getPieceAt(7, 6), whiteKing);
        Assert.assertEquals(concreteBoard.getPieceAt(7, 5), whiteRook);
    }
    
    /**
    1 d4 a6
    2 Qd3 b6
    3 Bd2 c6
    4 Nc3 d6
    */
    @Test
    public void testWhiteQueensideCastleRequest(){
        Point[] moveSeqSrc = {new Point(6, 3), new Point(1, 0), new Point(7, 3),
          new Point(1, 1), new Point(7, 2), new Point(1, 2), new Point(7, 1),
          new Point(1, 3)};
        Point[] moveSeqDst = {new Point(4, 3), new Point(2, 0), new Point(5, 3),
          new Point(2, 1), new Point(6, 3), new Point(2, 2), new Point(5, 2),
          new Point(2, 3)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);

        Assert.assertTrue(rigidArbiter.requestMove(7, 4, 7, 2));
        ChessPiece king = concreteBoard.getPieceAt(7, 2);
        ChessPiece rook = concreteBoard.getPieceAt(7, 3);

        Assert.assertEquals(new King(true), king);
        Assert.assertEquals(new Rook(true), rook);
    }
    
    /**
    1 a3 e5
    2 b3 Be7
    3 c3 Nf6
    4 d3
    */
    @Test
    public void testBlackKingsideCastleRequest(){
        Point[] moveSeqSrc = {new Point(6, 0), new Point(1, 4), new Point(6, 1),
          new Point(0, 5), new Point(6, 2), new Point(0, 6), new Point(6, 3)};
        Point[] moveSeqDst = {new Point(5, 0), new Point(3, 4), new Point(5, 1),
          new Point(1, 4), new Point(5, 2), new Point(2, 5), new Point(5, 3)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        Assert.assertTrue(rigidArbiter.requestMove(0, 4, 0, 6));

        King blackKing = new King(false);
        Rook blackRook = new Rook(false);

        Assert.assertEquals(concreteBoard.getPieceAt(0, 6), blackKing);
        Assert.assertEquals(concreteBoard.getPieceAt(0, 5), blackRook);
    }

    /**
    1 a3 d5
    2 b3 Qd6
    3 c3 Bd7
    4 d3 Nc6
    5 e3
    */
    @Test
    public void testBlackQueensideCastleRequest(){
        Point[] moveSeqSrc = {new Point(6, 0), new Point(1, 3), new Point(6, 1),
          new Point(0, 3), new Point(6, 2), new Point(0, 2), new Point(6, 3),
          new Point(0, 1), new Point(6, 4)};
        Point[] moveSeqDst = {new Point(5, 0), new Point(3, 3), new Point(5, 1),
          new Point(2, 3), new Point(5, 2), new Point(1, 3), new Point(5, 3),
          new Point(2, 2), new Point(5, 4)};
        executeMoveSequence(moveSeqSrc, moveSeqDst);
        Assert.assertTrue(rigidArbiter.requestMove(0, 4, 0, 2));

        King blackKing = new King(false);
        Rook blackRook = new Rook(false);

        Assert.assertEquals(concreteBoard.getPieceAt(0, 2), blackKing);
        Assert.assertEquals(concreteBoard.getPieceAt(0, 3), blackRook);
    }
}
