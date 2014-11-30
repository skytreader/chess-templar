package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.BlankBoard;
import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GridBoard;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

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

    /**
    Since pawns never ever go backward, a pawn's position on board determines
    if it is unmoved.
    */
    @Test
    public void testWhiteUnmoved(){
        try{
            Board testBoard = new GridBoard();

            // Test unmoved white pawn at (6, 1)
            Point[] whiteUnmoves = {new Point(5, 1), new Point(4, 1)};
            HashSet<Point> whiteUnmovesSet = new HashSet<Point>(Arrays.asList(whiteUnmoves));
            Set<Point> fromUnmovedWhite = whitePawn.getMoves(6, 1, testBoard);
            Assert.assertEquals(whiteUnmovesSet, fromUnmovedWhite);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing legal moves for white unmoved.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testBlackUnmoved(){
        try{
            Board testBoard = new GridBoard();

            // Test unmoved black pawn at (1, 1)
            Point[] blackUnmoves = {new Point(2, 1), new Point(3, 1)};
            HashSet<Point> blackUnmovesSet = new HashSet<Point>(Arrays.asList(blackUnmoves));
            Set<Point> fromUnmovedBlack = blackPawn.getMoves(1, 1, testBoard);
            Assert.assertEquals(blackUnmovesSet, fromUnmovedBlack);
        } catch(NotMeException nme){
            Assert.fail("NotMeException encountered!");
        }
    }
    
    /**
    Test that black pawn's capture moves are still covered by getMoves.
    */
    @Test
    public void testThreatenBlack(){
        try{
            Board testBoard = new GridBoard();

            // Move pawns into threatening positions and see that getMoves
            // includes the possibility of capture
            testBoard.move(1, 1, 3, 1);
            // Move white pawns into threatening positions
            testBoard.move(6, 0, 4, 0);
            testBoard.move(6, 2, 4, 2);
            
            // for black pawn at 3, 0
            Point[] legalMoves = {new Point(4, 0), new Point(4, 1),
              new Point(4, 2)};
            HashSet<Point> legalSet = new HashSet<Point>(Arrays.asList(legalMoves));
            Set<Point> fromPawn = blackPawn.getMoves(3, 1, testBoard);
            Assert.assertEquals(legalSet, fromPawn);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing legal moves for black captures.");
            nme.printStackTrace();
        }
    }
    
    /**
    Test that white pawn's capture moves are still covered by getMoves.
    */
    @Test
    public void testThreatenWhite(){
        try{
            Board testBoard = new GridBoard();

            // Move pawns into threatening positions and see that getMoves
            // includes the possibility of capture
            testBoard.move(1, 1, 3, 1);
            // Move white pawns into threatening positions
            testBoard.move(6, 0, 4, 0);
            testBoard.move(6, 2, 4, 2);

            // test the white pawn at 4, 0
            Point[] whiteMovesRight = {new Point(3, 0), new Point(3, 1)};
            HashSet<Point> whiteRight = new HashSet<Point>(Arrays.asList(whiteMovesRight));
            Set<Point> fromWhiteRight = whitePawn.getMoves(4, 0, testBoard);
            Assert.assertEquals(whiteRight, fromWhiteRight);

            Point[] whiteMovesLeft = {new Point(3, 2), new Point(3, 1)};
            HashSet<Point> whiteLeft = new HashSet<Point>(Arrays.asList(whiteMovesLeft));
            Set<Point> fromWhiteLeft = whitePawn.getMoves(4, 2, testBoard);
            Assert.assertEquals(whiteLeft, fromWhiteLeft);
        } catch(NotMeException nme){
            Assert.fail("NotMeException while testing legal moves for white captures.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testMoves(){
        try{
            Board testBoard = new GridBoard();
            
            // There is a black pawn at 1, 0 which has never moved yet.
            // Enumerate its legal moves.
            Point[] legalMoves = {new Point(2, 0), new Point(3, 0)};
            HashSet<Point> legalSet = new HashSet<Point>(Arrays.asList(legalMoves));
            Set<Point> fromPawn = blackPawn.getMoves(1, 0, testBoard);
            Assert.assertEquals(legalSet, fromPawn);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    Test pawns cannot capture forward
    */
    @Test
    public void testNoForwardCapture() throws NotMeException{
        Board testBoard = new GridBoard();
        testBoard.move(6, 4, 4, 4);
        testBoard.move(1, 4, 3, 4);
        HashSet<Point>legalMoves = new HashSet<Point>();
        Set<Point> fromPawn = whitePawn.getMoves(4, 4, testBoard);
        Assert.assertEquals(legalMoves, fromPawn);
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();
    
        // Try to use a white pawn to move the rook at (0, 0).
        whitePawn.getMoves(0, 0, testBoard);
    }
}
