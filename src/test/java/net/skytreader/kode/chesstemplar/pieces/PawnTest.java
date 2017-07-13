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
    private Board testBoard;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Before
    public void setUp(){
        whitePawn = new Pawn(ChessPiece.WHITE);
        blackPawn = new Pawn(!ChessPiece.WHITE);
        testBoard = new GridBoard();
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

    /**
    This case gave me problems in GameArbiterTest so it's here for posterity.
    */
    @Test
    public void testBlockedForward() throws NotMeException{
        testBoard.move(6, 4, 4, 4);
        testBoard.move(1, 4, 3, 4);
        testBoard.move(7, 6, 5, 5);
        testBoard.move(1, 3, 3, 3);
        
        // All the legal moves of the (4, 4) pawn: just a capture.
        Point[] kingsPawnMoves = {new Point(3, 3)};
        HashSet<Point> kingsPawnSet = new HashSet<Point>(Arrays.asList(kingsPawnMoves));

        Set<Point> actualMoves = whitePawn.getMoves(4, 4, testBoard);
        Assert.assertEquals(kingsPawnSet, actualMoves);
    }

    /**
    This case gave me problems in GameArbiterTest so it's here for posterity.
    */
    @Test
    public void testQueenBlockForwardWhite() throws NotMeException{
        Point[] moveSeqSrc = {new Point(6, 5), new Point(1, 4), new Point(6, 6),
          new Point(0, 3)};
        Point[] moveSeqDst = {new Point(4, 5), new Point(3, 4), new Point(4, 6),
          new Point(4, 7)};

        for(int i = 0; i < moveSeqSrc.length; i++){
            testBoard.move(moveSeqSrc[i].x, moveSeqSrc[i].y, moveSeqDst[i].x,
              moveSeqDst[i].y);
        }

        // No legal moves for any white piece, let alone the pawns
        // However, legality of moves (King safety) is not yet considered here
        // so the square ahead is clear for a move.
        Set<Point> rightMostPawnMoves = new HashSet<Point>();
        rightMostPawnMoves.add(new Point(5, 7));
        Set<Point> actualMoves = whitePawn.getMoves(6, 7, testBoard);
        Assert.assertEquals(rightMostPawnMoves, actualMoves);
    }

    /**
    This case gave me problems in GameArbiterTest so it's here for posterity.
    */
    @Test
    public void testQueenBlockForwardBlack() throws NotMeException{
        Point[] moveSeqSrc = {new Point(6, 4), new Point(1, 5), new Point(6, 0),
          new Point(1, 6), new Point(7, 3)};
        Point[] moveSeqDst = {new Point(4, 4), new Point(3, 5), new Point(4, 0),
          new Point(3, 6), new Point(3, 7)};

        for(int i = 0; i < moveSeqSrc.length; i++){
            testBoard.move(moveSeqSrc[i].x, moveSeqSrc[i].y, moveSeqDst[i].x,
              moveSeqDst[i].y);
        }

        // No legal moves for any white piece, let alone the pawns
        // However, legality of moves (King safety) is not yet considered here
        // so the square ahead is clear for a move.
        Set<Point> rightMostPawnMoves = new HashSet<Point>();
        rightMostPawnMoves.add(new Point(2, 7));
        Set<Point> actualMoves = blackPawn.getMoves(1, 7, testBoard);
        Assert.assertEquals(rightMostPawnMoves, actualMoves);
    }

    @Test
    public void testBlackMoves(){
        try{
            // There is a black pawn at 1, 0 which has never moved yet.
            // Enumerate its legal moves.
            Point[] legalMoves = {new Point(2, 0), new Point(3, 0)};
            HashSet<Point> legalSet = new HashSet<Point>(Arrays.asList(legalMoves));
            Set<Point> fromPawn = blackPawn.getMoves(1, 0, testBoard);
            Assert.assertEquals(legalSet, fromPawn);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing black legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    This test fails because the Fenton-Potter board conflicts with the convention
    that index (0, 0) is the a8 square.
    */
    @Test
    public void testWhiteMoves(){
        try{
            BlankBoard fentonPotter = new BlankBoard();
            Pawn b3Pawn = new Pawn(true);
            // Derived from (Fenton vs. Potter, 1875) with a few additional pieces
            fentonPotter.addPiece(new Rook(true), 7, 7);
            fentonPotter.addPiece(b3Pawn, 5, 1);
            fentonPotter.addPiece(new King(true), 5, 3);
            fentonPotter.addPiece(new Pawn(true), 4, 0);
            fentonPotter.addPiece(new Rook(false), 4, 1);
            fentonPotter.addPiece(new King(false), 3, 6);
            fentonPotter.addPiece(new Pawn(false), 2, 7);
            // This is an additional
            fentonPotter.addPiece(new Bishop(false), 1, 1);
            // for the pawn at (5, 1)/b3
            HashSet<Point> expectedPawnLegalMoves = new HashSet<Point>();
            Set<Point> fromPawn = b3Pawn.getMoves(5, 1, fentonPotter);
            Assert.assertEquals(expectedPawnLegalMoves, fromPawn);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing white legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    Test pawns cannot capture forward
    */
    @Test
    public void testNoForwardCapture() throws NotMeException{
        testBoard.move(6, 4, 4, 4);
        testBoard.move(1, 4, 3, 4);
        HashSet<Point>legalMoves = new HashSet<Point>();
        Set<Point> fromPawn = whitePawn.getMoves(4, 4, testBoard);
        Assert.assertEquals(legalMoves, fromPawn);
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
    
        // Try to use a white pawn to move the rook at (0, 0).
        whitePawn.getMoves(0, 0, testBoard);
    }
}
