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
            Set<Point> fromBishop = blackBishop.getLegalMoves(0, 2, testBoard);
            Assert.assertEquals(legalMoves, fromBishop);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing legal moves.");
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
