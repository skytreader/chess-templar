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

public class KnightTest{
    
    private Knight whiteKnight;
    private Knight blackKnight;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setUp(){
        whiteKnight = new Knight(ChessPiece.WHITE);
        blackKnight = new Knight(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteKnight.hashCode(), blackKnight.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        Knight anotherWhite = new Knight(ChessPiece.WHITE);
        Assert.assertTrue(whiteKnight.equals(whiteKnight));
        boolean forSymmetry = whiteKnight.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whiteKnight));
        }

        Knight thirdWhite = new Knight(ChessPiece.WHITE);
        boolean forTransitivity = whiteKnight.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whiteKnight.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whiteKnight.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whiteKnight.equals(blackKnight));
    }

    @Test
    public void testEqualsBlack(){
        Knight anotherBlack = new Knight(!ChessPiece.WHITE);
        Assert.assertTrue(blackKnight.equals(blackKnight));
        boolean forSymmetry = blackKnight.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackKnight));
        }

        Knight thirdBlack = new Knight(!ChessPiece.WHITE);
        boolean forTransitivity = blackKnight.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackKnight.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackKnight.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackKnight.equals(whiteKnight));
    }
}
