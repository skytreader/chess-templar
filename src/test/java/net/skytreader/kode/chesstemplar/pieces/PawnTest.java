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

public class PawnTest{
    
    private Pawn whitePawn;
    private Pawn blackPawn;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
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

        Pawn thirdBlack = new Pawn(!ChessPiece.WHITE);
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
        try{
            Board testBoard = new GridBoard();
            
            // There is a black pawn at 1, 0 which has never moved yet.
            // Enumerate its legal moves.
            Point[] legalMoves = {new Point(2, 0), new Point(3, 0)};
            HashSet<Point> legalSet = new HashSet<Point>(Arrays.asList(legalMoves));
            ChessPiece blackPawn = testBoard.getPieceAt(1, 0);
            Set<Point> fromPawn = blackPawn.getLegalMoves(1, 0, testBoard);
            Assert.assertEquals(legalSet, fromPawn);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing legal moves.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();
    
        // Try to use a white pawn to move the rook at (0, 0).
        whitePawn.getLegalMoves(0, 0, testBoard);
    }
}
