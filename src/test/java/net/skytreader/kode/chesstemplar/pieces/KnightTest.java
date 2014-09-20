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

public class KnightTest{
    
    private Knight whiteKnight;
    private Knight darkKnight;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setUp(){
        whiteKnight = new Knight(ChessPiece.WHITE);
        darkKnight = new Knight(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whiteKnight.hashCode(), darkKnight.hashCode());
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
        Assert.assertFalse(whiteKnight.equals(darkKnight));
    }

    @Test
    public void testEqualsBlack(){
        Knight anotherBlack = new Knight(!ChessPiece.WHITE);
        Assert.assertTrue(darkKnight.equals(darkKnight));
        boolean forSymmetry = darkKnight.equals(anotherBlack);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherBlack.equals(darkKnight));
        }

        Knight thirdBlack = new Knight(!ChessPiece.WHITE);
        boolean forTransitivity = darkKnight.equals(thirdBlack);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(darkKnight.equals(anotherBlack));
            Assert.assertTrue(anotherBlack.equals(thirdBlack));
        }

        Assert.assertFalse(darkKnight.equals(null));

        // Test that black does not equal white (transitive with testEqualsWhite).
        Assert.assertFalse(darkKnight.equals(whiteKnight));
    }

    @Test
    public void testInitialStateBlack(){
        try{
            Board testBoard = new GridBoard();

            Point[] queenSideMoves = {new Point(2, 0), new Point(2, 2)};
            HashSet<Point> queenSideSet = new HashSet<Point>(Arrays.asList(queenSideMoves));
            Set<Point> queenSideActual = darkKnight.getLegalMoves(0, 1, testBoard);
            Assert.assertEquals(queenSideSet, queenSideActual);

            Point[] kingSideMoves = {new Point(2, 5), new Point(2, 7)};
            HashSet<Point> kingSideSet = new HashSet<Point>(Arrays.asList(kingSideMoves));
            Set<Point> kingSideActual = darkKnight.getLegalMoves(0, 6, testBoard);
            Assert.assertEquals(kingSideSet, kingSideActual);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing intial state for black.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testInitialStateWhite(){
        try{
            Board testBoard = new GridBoard();

            Point[] queenSideMoves = {new Point(5, 0), new Point(5, 2)};
            HashSet<Point> queenSideSet = new HashSet<Point>(Arrays.asList(queenSideMoves));
            Set<Point> queenSideActual = whiteKnight.getLegalMoves(7, 1, testBoard);
            Assert.assertEquals(queenSideSet, queenSideActual);

            Point[] kingSideMoves = {new Point(5, 5), new Point(5, 7)};
            HashSet<Point> kingSideSet = new HashSet<Point>(Arrays.asList(kingSideMoves));
            Set<Point> kingSideActual = whiteKnight.getLegalMoves(7, 6, testBoard);
            Assert.assertEquals(kingSideSet, kingSideActual);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing intial state for white.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCommonMovement(){
        try{
            BlankBoard testBoard = new BlankBoard();

            // Add the white knight to 4, 4
            testBoard.addPiece(whiteKnight, 4, 4);
            Point[] expectedMoves = {new Point(2, 3), new Point(3, 2),
              new Point(5, 2), new Point(6, 3), new Point(2, 5), new Point(3, 6),
              new Point(5, 6), new Point(6, 5)};
            HashSet<Point> expectedMoveSet = new HashSet<Point>(Arrays.asList(expectedMoves));
            Set<Point> actualMovesWhite = whiteKnight.getLegalMoves(4, 4, testBoard);
            Assert.assertEquals(expectedMoveSet, actualMovesWhite);

            testBoard.removePiece(4, 4);
            testBoard.addPiece(darkKnight, 4, 4);
            Set<Point> actualMovesBlack = darkKnight.getLegalMoves(4, 4, testBoard);
            Assert.assertEquals(expectedMoveSet, actualMovesBlack);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing common movement.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board board = new GridBoard();

        whiteKnight.getLegalMoves(0, 0, board);
    }
}
