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

public class RookTest{
    
    private Rook whiteRook;
    private Rook blackRook;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp(){
        whiteRook = new Rook(ChessPiece.WHITE);
        blackRook = new Rook(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteRook.hashCode(), blackRook.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        Rook anotherWhite = new Rook(ChessPiece.WHITE);
        Assert.assertTrue(whiteRook.equals(whiteRook));
        boolean forSymmetry = whiteRook.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whiteRook));
        }

        Rook thirdWhite = new Rook(ChessPiece.WHITE);
        boolean forTransitivity = whiteRook.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whiteRook.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whiteRook.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whiteRook.equals(blackRook));
    }

    @Test
    public void testEqualsBlack(){
        Rook anotherBlack = new Rook(!ChessPiece.WHITE);
        Assert.assertTrue(blackRook.equals(blackRook));
        boolean forSymmetry = blackRook.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackRook));
        }

        Rook thirdBlack = new Rook(!ChessPiece.WHITE);
        boolean forTransitivity = blackRook.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackRook.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackRook.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackRook.equals(whiteRook));
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();
    
        // Try to use a white rook to move the black rook at (0, 0).
        whiteRook.getLegalMoves(0, 0, testBoard);
    }
}
