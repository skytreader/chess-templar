package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.BlankBoard;
import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GridBoard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

public class QueenTest{
    
    private Queen whiteQueen;
    private Queen blackQueen;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp(){
        whiteQueen = new Queen(ChessPiece.WHITE);
        blackQueen = new Queen(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteQueen.hashCode(), blackQueen.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        Queen anotherWhite = new Queen(ChessPiece.WHITE);
        Assert.assertTrue(whiteQueen.equals(whiteQueen));
        boolean forSymmetry = whiteQueen.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whiteQueen));
        }

        Queen thirdWhite = new Queen(ChessPiece.WHITE);
        boolean forTransitivity = whiteQueen.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whiteQueen.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whiteQueen.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whiteQueen.equals(blackQueen));
    }

    @Test
    public void testEqualsBlack(){
        Queen anotherBlack = new Queen(!ChessPiece.WHITE);
        Assert.assertTrue(blackQueen.equals(blackQueen));
        boolean forSymmetry = blackQueen.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackQueen));
        }

        Queen thirdBlack = new Queen(!ChessPiece.WHITE);
        boolean forTransitivity = blackQueen.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackQueen.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackQueen.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackQueen.equals(whiteQueen));
    }

    @Test
    public void testInitialConfiguration(){
        try{
            Board testBoard = new GridBoard();
            HashSet<Point> emptySet = new HashSet<Point>();
            Set<Point> black00 = blackQueen.getLegalMoves(0, 0, testBoard);
            Set<Point> black07 = blackQueen.getLegalMoves(0, 7, testBoard);
            Set<Point> white70 = whiteQueen.getLegalMoves(7, 0, testBoard);
            Set<Point> white77 = whiteQueen.getLegalMoves(7, 7, testBoard);

            Assert.assertEquals(black00, emptySet);
            Assert.assertEquals(black07, emptySet);
            Assert.assertEquals(white70, emptySet);
            Assert.assertEquals(white77, emptySet);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing initial configuration.");
            nme.printStackTrace();
        }
    }
}
