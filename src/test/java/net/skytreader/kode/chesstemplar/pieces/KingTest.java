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

    @Test
    public void testInitialState(){
        try{
            Board board = new GridBoard();

            // Kings at 0,4 and 7,4
            HashSet<Point> blackMoves = new HashSet<Point>();
            Set<Point> fromBlack = blackKing.getLegalMoves(0, 4, board);
            Assert.assertEquals(blackMoves, fromBlack);

            HashSet<Point> whiteMoves = new HashSet<Point>();
            Set<Point> fromWhite = whiteKing.getLegalMoves(7, 4, board);
            Assert.assertEquals(whiteMoves, fromWhite);
        } catch(NotMeException nme){
            Assert.fail("NotMeException when testing King's initial state.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCommonMovesWhite(){
        try{
            BlankBoard board = new BlankBoard();

            // Put a white king at 4,4
            board.addPiece(whiteKing, 4, 4);
            Point[] expectedMoves = {new Point(4, 5), new Point(3, 4),
              new Point(3, 3), new Point(3, 5), new Point(4, 3), new Point(5, 3),
              new Point(5, 4), new Point(5, 5)};
            HashSet<Point> expectedSet = new HashSet<Point>(Arrays.asList(expectedMoves));
            Set<Point> actual1 = whiteKing.getLegalMoves(4, 4, board);
            Assert.assertEquals(expectedSet, actual1);

            // Put a white pawn in front of the white king
            board.addPiece(new Pawn(true), 3, 4);
            // remove 3, 4 in expected moves
            expectedSet.remove(new Point(3, 4));
            Set<Point> actual2 = whiteKing.getLegalMoves(3, 4, board);
            Assert.assertEquals(expectedSet, actual2);

            // Make that a black pawn
            board.removePiece(3, 4);
            board.addPiece(new Pawn(false), 3, 4);
            expectedSet.add(new Point(3, 4));
            Set<Point> actual3 = whiteKing.getLegalMoves(3, 4, board);
            Assert.assertEquals(expectedSet, actual3);
        } catch(NotMeException nme){
            Assert.fail("NotMeException when testing common moves for a King.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCommonMovesBlack(){
        try{
            BlankBoard board = new BlankBoard();

            // Put a black king at 4,4
            board.addPiece(blackKing, 4, 4);
            Point[] expectedMoves = {new Point(4, 5), new Point(3, 4),
              new Point(3, 3), new Point(3, 5), new Point(4, 3), new Point(5, 3),
              new Point(5, 4), new Point(5, 5)};
            HashSet<Point> expectedSet = new HashSet<Point>(Arrays.asList(expectedMoves));
            Set<Point> actual1 = blackKing.getLegalMoves(4, 4, board);
            Assert.assertEquals(expectedSet, actual1);

            // Put a black pawn in front of the black king
            board.addPiece(new Pawn(true), 3, 4);
            // remove 3, 4 in expected moves
            expectedSet.remove(new Point(3, 4));
            Set<Point> actual2 = blackKing.getLegalMoves(3, 4, board);
            Assert.assertEquals(expectedSet, actual2);

            // Make that a black pawn
            board.removePiece(3, 4);
            board.addPiece(new Pawn(false), 3, 4);
            expectedSet.add(new Point(3, 4));
            Set<Point> actual3 = blackKing.getLegalMoves(3, 4, board);
            Assert.assertEquals(expectedSet, actual3);
        } catch(NotMeException nme){
            Assert.fail("NotMeException when testing common moves for a King.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();

        // Use the white king to get the moves of the black king
        whiteKing.getLegalMoves(0, 4, testBoard);
    }
}
