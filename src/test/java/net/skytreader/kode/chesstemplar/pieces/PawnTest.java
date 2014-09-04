package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;

import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GridBoard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PawnTest{
    
    private Pawn whitePawn;
    private Pawn blackPawn;
    
    @Before
    public void setUp(){
        whitePawn = new Pawn(ChessPiece.WHITE);
        blackPawn = new Pawn(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whitePawn.hashCode(), blackPawn.hashCode());
    }

    @Test
    public void testEqualsWhite(){
        Pawn anotherWhite = new Pawn(ChessPiece.WHITE);
        Assert.assertTrue(whitePawn.equals(whitePawn));
        boolean forSymmetry = whitePawn.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whitePawn));
        }

        Pawn thirdWhite = new Pawn(ChessPiece.WHITE);
        boolean forTransitivity = whitePawn.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whitePawn.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }

        Assert.assertFalse(whitePawn.equals(null));
        
        // Test that white does not equal black (transitive with testEqualsBlack).
        Assert.assertFalse(whitePawn.equals(blackPawn));
    }

    @Test
    public void testEqualsBlack(){
        Pawn anotherBlack = new Pawn(!ChessPiece.WHITE);
        Assert.assertTrue(blackPawn.equals(blackPawn));
        boolean forSymmetry = blackPawn.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(blackPawn));
        }

        Pawn thirdBlack = new Pawn(ChessPiece.WHITE);
        boolean forTransitivity = blackPawn.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(blackPawn.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(blackPawn.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(blackPawn.equals(whitePawn));
    }

    @Test
    public void testLegalMoves(){
        Board testBoard = new GridBoard();
        //HashSet<Point> moveSet = new HashSet<Point>(Arrays.asList(
    }
}
