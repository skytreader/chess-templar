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

public class KingTest{
    
    private King whiteKing;
    private King blackKing;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setUp(){
        whiteKing = new King(ChessPiece.WHITE);
        blackKing = new King(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteKing.hashCode(), blackKing.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        King anotherWhite = new King(ChessPiece.WHITE);
        Assert.assertTrue(whiteKing.equals(whiteKing));
        boolean forSymmetry = whiteKing.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whiteKing));
        }

        King thirdWhite = new King(ChessPiece.WHITE);
        boolean forTransitivity = whiteKing.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whiteKing.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whiteKing.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whiteKing.equals(blackKing));
    }

    @Test
    public void testEqualsBlack(){
        King anotherBlack = new King(!ChessPiece.WHITE);
        Assert.assertTrue(blackKing.equals(blackKing));
        boolean forSymmetry = blackKing.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackKing));
        }

        King thirdBlack = new King(!ChessPiece.WHITE);
        boolean forTransitivity = blackKing.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackKing.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackKing.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackKing.equals(whiteKing));
    }
}
