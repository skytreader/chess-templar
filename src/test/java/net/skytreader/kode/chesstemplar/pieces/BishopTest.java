package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GridBoard;
import net.skytreader.kode.chesstemplar.BlankBoard;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

public class BishopTest{
    
    private Bishop whiteBishop;
    private Bishop blackBishop;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setUp(){
        whiteBishop = new Bishop(ChessPiece.WHITE);
        blackBishop = new Bishop(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteBishop.hashCode(), blackBishop.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        Bishop anotherWhite = new Bishop(ChessPiece.WHITE);
        Assert.assertTrue(whiteBishop.equals(whiteBishop));
        boolean forSymmetry = whiteBishop.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whiteBishop));
        }

        Bishop thirdWhite = new Bishop(ChessPiece.WHITE);
        boolean forTransitivity = whiteBishop.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whiteBishop.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whiteBishop.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whiteBishop.equals(blackBishop));
    }

    @Test
    public void testEqualsBlack(){
        Bishop anotherBlack = new Bishop(!ChessPiece.WHITE);
        Assert.assertTrue(blackBishop.equals(blackBishop));
        boolean forSymmetry = blackBishop.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackBishop));
        }

        Bishop thirdBlack = new Bishop(!ChessPiece.WHITE);
        boolean forTransitivity = blackBishop.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackBishop.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackBishop.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackBishop.equals(whiteBishop));
    }
    
    /**
    Test the legal moves allowed for a Bishop in the game's initial configuration.

    The legal moves: nothing.
    */
    @Test
    public void testInitialMoves(){
        try{
            Board testBoard = new GridBoard();

            HashSet<Point> legalMoves = new HashSet<Point>();
            Set<Point> fromBishop1 = blackBishop.getMoves(0, 2, testBoard);
            Set<Point> fromBishop2 = blackBishop.getMoves(0, 5, testBoard);
            Set<Point> fromBishop3 = whiteBishop.getMoves(7, 2, testBoard);
            Set<Point> fromBishop4 = whiteBishop.getMoves(7, 5, testBoard);
            Assert.assertEquals(legalMoves, fromBishop1);
            Assert.assertEquals(legalMoves, fromBishop2);
            Assert.assertEquals(legalMoves, fromBishop3);
            Assert.assertEquals(legalMoves, fromBishop4);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    The move scenario we'll most likely encounter most throughout a game: pieces
    are moved and the Bishop has free reign. This tests the white bishops.
    */
    @Test
    public void testCommonMovesWhite(){
        try{
            Board testBoard = new GridBoard();

            //Move some pawns around
            testBoard.move(6, 3, 4, 3);
            testBoard.move(6, 4, 4, 4);

            HashSet<Point> whiteMoves1 = new HashSet<Point>();
            whiteMoves1.add(new Point(6, 3));
            whiteMoves1.add(new Point(5, 4));
            whiteMoves1.add(new Point(4, 5));
            whiteMoves1.add(new Point(3, 6));
            whiteMoves1.add(new Point(2, 7));
            Set<Point> fromWhiteBishop1 = whiteBishop.getMoves(7, 2, testBoard);
            Assert.assertEquals(whiteMoves1, fromWhiteBishop1);

            HashSet<Point> whiteMoves2 = new HashSet<Point>();
            whiteMoves2.add(new Point(6, 4));
            whiteMoves2.add(new Point(5, 3));
            whiteMoves2.add(new Point(4, 2));
            whiteMoves2.add(new Point(3, 1));
            whiteMoves2.add(new Point(2, 0));
            Set<Point> fromWhiteBishop2 = whiteBishop.getMoves(7, 5, testBoard);
            Assert.assertEquals(whiteMoves2, fromWhiteBishop2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    The move scenario we'll most likely encounter most throughout a game: pieces
    are moved and the Bishop has free reign. This tests the black bishops.
    */
    @Test
    public void testCommonMovesBlack(){
        try{
            Board testBoard = new GridBoard();

            //Move some pawns around
            testBoard.move(1, 3, 3, 3);
            testBoard.move(1, 4, 3, 4);

            HashSet<Point> blackMoves1 = new HashSet<Point>();
            blackMoves1.add(new Point(1, 3));
            blackMoves1.add(new Point(2, 4));
            blackMoves1.add(new Point(3, 5));
            blackMoves1.add(new Point(4, 6));
            blackMoves1.add(new Point(5, 7));
            Set<Point> fromBlackBishop1 = blackBishop.getMoves(0, 2, testBoard);
            Assert.assertEquals(blackMoves1, fromBlackBishop1);

            HashSet<Point> blackMoves2 = new HashSet<Point>();
            blackMoves2.add(new Point(1, 4));
            blackMoves2.add(new Point(2, 3));
            blackMoves2.add(new Point(3, 2));
            blackMoves2.add(new Point(4, 1));
            blackMoves2.add(new Point(5, 0));
            Set<Point> fromBlackBishop2 = blackBishop.getMoves(0, 5, testBoard);
            Assert.assertEquals(blackMoves2, fromBlackBishop2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCaptureScenarioWhite(){
        try{
            Board testBoard = new GridBoard();

            // Move pawns in the line of fire of the white bishops
            testBoard.move(1, 1, 3, 1);
            testBoard.move(1, 6, 3, 6);

            // Free up our Bishops
            testBoard.move(6, 3, 4, 3);
            testBoard.move(6, 4, 4, 4);

            HashSet<Point> whiteMoves1 = new HashSet<Point>();
            whiteMoves1.add(new Point(6, 3));
            whiteMoves1.add(new Point(5, 4));
            whiteMoves1.add(new Point(4, 5));
            whiteMoves1.add(new Point(3, 6));
            Set<Point> fromWhiteBishop1 = whiteBishop.getMoves(7, 2, testBoard);
            Assert.assertEquals(whiteMoves1, fromWhiteBishop1);

            HashSet<Point> whiteMoves2 = new HashSet<Point>();
            whiteMoves2.add(new Point(6, 4));
            whiteMoves2.add(new Point(5, 3));
            whiteMoves2.add(new Point(4, 2));
            whiteMoves2.add(new Point(3, 1));
            Set<Point> fromWhiteBishop2 = whiteBishop.getMoves(7, 5, testBoard);
            Assert.assertEquals(whiteMoves2, fromWhiteBishop2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing for capture scenario (white).");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCaptureScenarioBlack(){
        try{
            Board testBoard = new GridBoard();

            // Move pawns in the line of fire of the black bishops
            testBoard.move(6, 1, 4, 1);
            testBoard.move(6, 6, 4, 6);

            // Free up our Bishops
            testBoard.move(1, 3, 3, 3);
            testBoard.move(1, 4, 3, 4);

            HashSet<Point> blackMoves1 = new HashSet<Point>();
            blackMoves1.add(new Point(1, 3));
            blackMoves1.add(new Point(2, 4));
            blackMoves1.add(new Point(3, 5));
            blackMoves1.add(new Point(4, 6));
            Set<Point> fromBlackBishop1 = blackBishop.getMoves(0, 2, testBoard);
            Assert.assertEquals(blackMoves1, fromBlackBishop1);

            HashSet<Point> blackMoves2 = new HashSet<Point>();
            blackMoves2.add(new Point(1, 4));
            blackMoves2.add(new Point(2, 3));
            blackMoves2.add(new Point(3, 2));
            blackMoves2.add(new Point(4, 1));
            Set<Point> fromBlackBishop2 = blackBishop.getMoves(0, 5, testBoard);
            Assert.assertEquals(blackMoves2, fromBlackBishop2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing for capture scenario (black).");
            nme.printStackTrace();
        }
    }

    @Test
    public void testSoloWhiteBishop(){
        try{
            BlankBoard board = new BlankBoard();
            
            // 4, 4 is a white square
            board.addPiece(whiteBishop, 4, 4);
            HashSet<Point> expectedMoves = new HashSet<Point>();

            // Let's do this per "wing" of the cross
            expectedMoves.add(new Point(3, 5));
            expectedMoves.add(new Point(2, 6));
            expectedMoves.add(new Point(1, 7));

            expectedMoves.add(new Point(3, 3));
            expectedMoves.add(new Point(2, 2));
            expectedMoves.add(new Point(1, 1));
            expectedMoves.add(new Point(0, 0));
            
            expectedMoves.add(new Point(5, 5));
            expectedMoves.add(new Point(6, 6));
            expectedMoves.add(new Point(7, 7));

            expectedMoves.add(new Point(5, 3));
            expectedMoves.add(new Point(6, 2));
            expectedMoves.add(new Point(7, 1));

            Set<Point> actualMoves = whiteBishop.getMoves(4, 4, board);
            Assert.assertEquals(expectedMoves, actualMoves);

            // Add a piece of the smae color in white Bishop's way
            // Add a piece to 3,5 of same color and that wing should not exist
            board.addPiece(new Pawn(true), 3, 5);
            HashSet<Point> expectedMoves2 = new HashSet<Point>();

            expectedMoves2.add(new Point(3, 3));
            expectedMoves2.add(new Point(2, 2));
            expectedMoves2.add(new Point(1, 1));
            expectedMoves2.add(new Point(0, 0));
            
            expectedMoves2.add(new Point(5, 5));
            expectedMoves2.add(new Point(6, 6));
            expectedMoves2.add(new Point(7, 7));

            expectedMoves2.add(new Point(5, 3));
            expectedMoves2.add(new Point(6, 2));
            expectedMoves2.add(new Point(7, 1));

            Set<Point> actualMoves2 = whiteBishop.getMoves(4, 4, board);
            Assert.assertEquals(expectedMoves2, actualMoves2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing for solo white Bishop.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testSoloBlackBishop(){
        try{
            BlankBoard board = new BlankBoard();
            
            // 4, 4 is a white square
            board.addPiece(blackBishop, 4, 4);
            HashSet<Point> expectedMoves = new HashSet<Point>();

            // Let's do this per "wing" of the cross
            expectedMoves.add(new Point(3, 5));
            expectedMoves.add(new Point(2, 6));
            expectedMoves.add(new Point(1, 7));

            expectedMoves.add(new Point(3, 3));
            expectedMoves.add(new Point(2, 2));
            expectedMoves.add(new Point(1, 1));
            expectedMoves.add(new Point(0, 0));
            
            expectedMoves.add(new Point(5, 5));
            expectedMoves.add(new Point(6, 6));
            expectedMoves.add(new Point(7, 7));

            expectedMoves.add(new Point(5, 3));
            expectedMoves.add(new Point(6, 2));
            expectedMoves.add(new Point(7, 1));

            Set<Point> actualMoves = blackBishop.getMoves(4, 4, board);
            Assert.assertEquals(expectedMoves, actualMoves);

        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing for solo black Bishop.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();

        // Use this Bishop to get the moves of a pawn
        whiteBishop.getMoves(0, 1, testBoard);
    }
}
