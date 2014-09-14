package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GridBoard;

import org.junit.Assert;
import org.junit.Before;
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
    public void testInitialLegalMoves(){
        try{
            Board testBoard = new GridBoard();

            HashSet<Point> legalMoves = new HashSet<Point>();
            Set<Point> fromBishop1 = blackBishop.getLegalMoves(0, 2, testBoard);
            Set<Point> fromBishop2 = blackBishop.getLegalMoves(0, 5, testBoard);
            Set<Point> fromBishop3 = whiteBishop.getLegalMoves(7, 2, testBoard);
            Set<Point> fromBishop4 = whiteBishop.getLegalMoves(7, 5, testBoard);
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
    public void testCommonLegalMovesWhite(){
        try{
            Board testBoard = new GridBoard();

            //Move some pawns around
            testBoard.move(6, 3, 4, 3);
            testBoard.move(7, 4, 4, 4);

            HashSet<Point> whiteLegalMoves1 = new HashSet<Point>();
            whiteLegalMoves1.add(new Point(6, 3));
            whiteLegalMoves1.add(new Point(5, 4));
            whiteLegalMoves1.add(new Point(4, 5));
            whiteLegalMoves1.add(new Point(3, 6));
            whiteLegalMoves1.add(new Point(2, 7));
            Set<Point> fromWhiteBishop1 = whiteBishop.getLegalMoves(7, 2, testBoard);
            Assert.assertEquals(whiteLegalMoves1, fromWhiteBishop1);

            HashSet<Point> whiteLegalMoves2 = new HashSet<Point>();
            whiteLegalMoves2.add(new Point(6, 4));
            whiteLegalMoves2.add(new Point(5, 3));
            whiteLegalMoves2.add(new Point(4, 2));
            whiteLegalMoves2.add(new Point(3, 1));
            whiteLegalMoves2.add(new Point(2, 0));
            Set<Point> fromWhiteBishop2 = whiteBishop.getLegalMoves(7, 5, testBoard);
            Assert.assertEquals(whiteLegalMoves2, fromWhiteBishop2);
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
    public void testCommonLegalMovesBlack(){
        try{
            Board testBoard = new GridBoard();

            //Move some pawns around
            testBoard.move(1, 3, 3, 3);
            testBoard.move(1, 4, 3, 4);

            HashSet<Point> blackLegalMoves1 = new HashSet<Point>();
            blackLegalMoves1.add(new Point(1, 3));
            blackLegalMoves1.add(new Point(2, 4));
            blackLegalMoves1.add(new Point(3, 5));
            blackLegalMoves1.add(new Point(4, 6));
            blackLegalMoves1.add(new Point(5, 7));
            Set<Point> fromBlackBishop1 = blackBishop.getLegalMoves(0, 2, testBoard);
            Assert.assertEquals(blackLegalMoves1, fromBlackBishop1);

            HashSet<Point> blackLegalMoves2 = new HashSet<Point>();
            blackLegalMoves2.add(new Point(1, 4));
            blackLegalMoves2.add(new Point(2, 3));
            blackLegalMoves2.add(new Point(3, 2));
            blackLegalMoves2.add(new Point(4, 1));
            blackLegalMoves2.add(new Point(5, 0));
            Set<Point> fromBlackBishop2 = blackBishop.getLegalMoves(0, 5, testBoard);
            Assert.assertEquals(blackLegalMoves2, fromBlackBishop2);
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
            testBoard.move(7, 4, 4, 4);

            HashSet<Point> whiteLegalMoves1 = new HashSet<Point>();
            whiteLegalMoves1.add(new Point(6, 3));
            whiteLegalMoves1.add(new Point(5, 4));
            whiteLegalMoves1.add(new Point(4, 5));
            whiteLegalMoves1.add(new Point(3, 6));
            Set<Point> fromWhiteBishop1 = whiteBishop.getLegalMoves(7, 2, testBoard);
            Assert.assertEquals(whiteLegalMoves1, fromWhiteBishop1);

            HashSet<Point> whiteLegalMoves2 = new HashSet<Point>();
            whiteLegalMoves2.add(new Point(6, 4));
            whiteLegalMoves2.add(new Point(5, 3));
            whiteLegalMoves2.add(new Point(4, 2));
            whiteLegalMoves2.add(new Point(3, 1));
            Set<Point> fromWhiteBishop2 = whiteBishop.getLegalMoves(7, 5, testBoard);
            Assert.assertEquals(whiteLegalMoves2, fromWhiteBishop2);
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

            HashSet<Point> blackLegalMoves1 = new HashSet<Point>();
            blackLegalMoves1.add(new Point(1, 3));
            blackLegalMoves1.add(new Point(2, 4));
            blackLegalMoves1.add(new Point(3, 5));
            blackLegalMoves1.add(new Point(4, 6));
            Set<Point> fromBlackBishop1 = blackBishop.getLegalMoves(7, 2, testBoard);
            Assert.assertEquals(blackLegalMoves1, fromBlackBishop1);

            HashSet<Point> blackLegalMoves2 = new HashSet<Point>();
            blackLegalMoves2.add(new Point(1, 4));
            blackLegalMoves2.add(new Point(2, 3));
            blackLegalMoves2.add(new Point(3, 2));
            blackLegalMoves2.add(new Point(4, 1));
            Set<Point> fromBlackBishop2 = blackBishop.getLegalMoves(7, 5, testBoard);
            Assert.assertEquals(blackLegalMoves2, fromBlackBishop2);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing for capture scenario (black).");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();

        // Use this Bishop to get the moves of a pawn
        whiteBishop.getLegalMoves(0, 1, testBoard);
    }
}
