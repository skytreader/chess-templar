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

public class RookTest{
    
    private Rook whiteRook;
    private Rook blackRook;
    private BlankBoard testBoard;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp(){
        whiteRook = new Rook(ChessPiece.WHITE);
        blackRook = new Rook(!ChessPiece.WHITE);
        testBoard = new BlankBoard();
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
    public void testInitialConfiguration(){
        try{
            Board testBoard = new GridBoard();
            HashSet<Point> emptySet = new HashSet<Point>();
            Set<Point> black00 = blackRook.getMoves(0, 0, testBoard);
            Set<Point> black07 = blackRook.getMoves(0, 7, testBoard);
            Set<Point> white70 = whiteRook.getMoves(7, 0, testBoard);
            Set<Point> white77 = whiteRook.getMoves(7, 7, testBoard);

            Assert.assertEquals(black00, emptySet);
            Assert.assertEquals(black07, emptySet);
            Assert.assertEquals(white70, emptySet);
            Assert.assertEquals(white77, emptySet);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing initial configuration.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCornerCasesBlack(){
        try{
            // Put it at all corners!
            testBoard.addPiece(blackRook, 0, 0);
            HashSet<Point> legalMoves00 = new HashSet<Point>();
            
            for(int i = 1; i < 8; i++){
                legalMoves00.add(new Point(0, i));
                legalMoves00.add(new Point(i, 0));
            }

            Set<Point> actualMoves00 = blackRook.getMoves(0, 0, testBoard);
            Assert.assertEquals(legalMoves00, actualMoves00);
            testBoard.removePiece(0, 0);

            testBoard.addPiece(blackRook, 0, 7);
            HashSet<Point> legalMoves07 = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i == 0){
                    legalMoves07.add(new Point(0, i));
                } else if(i == 7){
                    legalMoves07.add(new Point(i, 7));
                } else {
                    legalMoves07.add(new Point(0, i));
                    legalMoves07.add(new Point(i, 7));
                }
            }

            Set<Point> actualMoves07 = blackRook.getMoves(0, 7, testBoard);
            Assert.assertEquals(legalMoves07, actualMoves07);
            testBoard.removePiece(0, 7);

            testBoard.addPiece(blackRook, 7, 0);
            HashSet<Point> legalMoves70 = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i == 0){
                    legalMoves70.add(new Point(i, 0));
                } else if(i == 7){
                    legalMoves70.add(new Point(7, i));
                } else{
                    legalMoves70.add(new Point(i, 0));
                    legalMoves70.add(new Point(7, i));
                }
            }

            Set<Point> actualMoves70 = blackRook.getMoves(7, 0, testBoard);
            Assert.assertEquals(legalMoves70, actualMoves70);
            testBoard.removePiece(7, 0);

            testBoard.addPiece(blackRook, 7, 7);
            HashSet<Point> legalMoves77 = new HashSet<Point>();

            for(int i = 0; i < 7; i++){
                legalMoves77.add(new Point(i, 7));
                legalMoves77.add(new Point(7, i));
            }

            Set<Point> actualMoves77 = blackRook.getMoves(7, 7, testBoard);
            Assert.assertEquals(legalMoves77, actualMoves77);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing corner cases.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCornerCasesWhite(){
        try{
            // Put it at all corners!
            testBoard.addPiece(whiteRook, 0, 0);
            HashSet<Point> legalMoves00 = new HashSet<Point>();
            
            for(int i = 1; i < 8; i++){
                legalMoves00.add(new Point(0, i));
                legalMoves00.add(new Point(i, 0));
            }

            Set<Point> actualMoves00 = whiteRook.getMoves(0, 0, testBoard);
            Assert.assertEquals(legalMoves00, actualMoves00);
            testBoard.removePiece(0, 0);

            testBoard.addPiece(whiteRook, 0, 7);
            HashSet<Point> legalMoves07 = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i == 0){
                    legalMoves07.add(new Point(0, i));
                } else if(i == 7){
                    legalMoves07.add(new Point(i, 7));
                } else {
                    legalMoves07.add(new Point(0, i));
                    legalMoves07.add(new Point(i, 7));
                }
            }

            Set<Point> actualMoves07 = whiteRook.getMoves(0, 7, testBoard);
            Assert.assertEquals(legalMoves07, actualMoves07);
            testBoard.removePiece(0, 7);

            testBoard.addPiece(whiteRook, 7, 0);
            HashSet<Point> legalMoves70 = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i == 0){
                    legalMoves70.add(new Point(i, 0));
                } else if(i == 7){
                    legalMoves70.add(new Point(7, i));
                } else{
                    legalMoves70.add(new Point(i, 0));
                    legalMoves70.add(new Point(7, i));
                }
            }

            Set<Point> actualMoves70 = whiteRook.getMoves(7, 0, testBoard);
            Assert.assertEquals(legalMoves70, actualMoves70);
            testBoard.removePiece(7, 0);

            testBoard.addPiece(whiteRook, 7, 7);
            HashSet<Point> legalMoves77 = new HashSet<Point>();

            for(int i = 0; i < 7; i++){
                legalMoves77.add(new Point(i, 7));
                legalMoves77.add(new Point(7, i));
            }

            Set<Point> actualMoves77 = whiteRook.getMoves(7, 7, testBoard);
            Assert.assertEquals(legalMoves77, actualMoves77);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing corner cases.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCommonMovesWhite(){
        try{
            testBoard.addPiece(whiteRook, 4, 4);
            HashSet<Point> expectedSet = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i != 4){
                    expectedSet.add(new Point(i, 4));
                    expectedSet.add(new Point(4, i));
                }
            }

            Set<Point> actualMoves = whiteRook.getMoves(4, 4, testBoard);
            Assert.assertEquals(expectedSet, actualMoves);

        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves for white.");
        }
    }

    @Test
    public void testCaptureScenarioWhite() throws NotMeException{
        testBoard.addPiece(whiteRook, 4, 4);
        testBoard.addPiece(new Pawn(false), 1, 4);
        testBoard.addPiece(new Pawn(false), 6, 4);
        testBoard.addPiece(new Pawn(false), 4, 1);
        testBoard.addPiece(new Pawn(false), 4, 6);
        HashSet<Point> expectedSet = new HashSet<Point>();

        for(int i = 0; i < 8; i++){
            if(i != 4){
                expectedSet.add(new Point(i, 4));
                expectedSet.add(new Point(4, i));
            }
        }

        // Remove squares blocked by pawns
        expectedSet.remove(new Point(0, 4));
        expectedSet.remove(new Point(7, 4));
        expectedSet.remove(new Point(4, 0));
        expectedSet.remove(new Point(4, 7));

        Set<Point> actualMoves = whiteRook.getMoves(4, 4, testBoard);
        Assert.assertEquals(expectedSet, actualMoves);
    }

    /**
    Test that Rooks shouldn't capture their those with the same color.
    */
    @Test
    public void testNoCaptureScenarioWhite() throws NotMeException{
        testBoard.addPiece(whiteRook, 4, 4);
        testBoard.addPiece(new Pawn(true), 1, 4);
        testBoard.addPiece(new Pawn(true), 6, 4);
        testBoard.addPiece(new Pawn(true), 4, 1);
        testBoard.addPiece(new Pawn(true), 4, 6);
        HashSet<Point> expectedSet = new HashSet<Point>();

        for(int i = 0; i < 8; i++){
            if(i != 4){
                expectedSet.add(new Point(i, 4));
                expectedSet.add(new Point(4, i));
            }
        }

        // Remove squares blocked by pawns
        expectedSet.remove(new Point(0, 4));
        expectedSet.remove(new Point(1, 4));
        expectedSet.remove(new Point(6, 4));
        expectedSet.remove(new Point(7, 4));
        expectedSet.remove(new Point(4, 0));
        expectedSet.remove(new Point(4, 1));
        expectedSet.remove(new Point(4, 6));
        expectedSet.remove(new Point(4, 7));

        Set<Point> actualMoves = whiteRook.getMoves(4, 4, testBoard);
        Assert.assertEquals(expectedSet, actualMoves);
    }

    @Test
    public void testCommonMovesBlack(){
        try{
            testBoard.addPiece(blackRook, 4, 4);
            HashSet<Point> expectedSet = new HashSet<Point>();

            for(int i = 0; i < 8; i++){
                if(i != 4){
                    expectedSet.add(new Point(i, 4));
                    expectedSet.add(new Point(4, i));
                }
            }

            Set<Point> actualMoves = blackRook.getMoves(4, 4, testBoard);
            Assert.assertEquals(expectedSet, actualMoves);

        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves for black.");
        }
    }

    @Test
    public void testCaptureScenarioBlack() throws NotMeException{
        testBoard.addPiece(blackRook, 4, 4);
        testBoard.addPiece(new Pawn(true), 1, 4);
        testBoard.addPiece(new Pawn(true), 6, 4);
        testBoard.addPiece(new Pawn(true), 4, 1);
        testBoard.addPiece(new Pawn(true), 4, 6);
        HashSet<Point> expectedSet = new HashSet<Point>();

        for(int i = 0; i < 8; i++){
            if(i != 4){
                expectedSet.add(new Point(i, 4));
                expectedSet.add(new Point(4, i));
            }
        }

        // Remove squares blocked by Pawns
        expectedSet.remove(new Point(0, 4));
        expectedSet.remove(new Point(7, 4));
        expectedSet.remove(new Point(4, 0));
        expectedSet.remove(new Point(4, 7));

        Set<Point> actualMoves = blackRook.getMoves(4, 4, testBoard);
        Assert.assertEquals(expectedSet, actualMoves);
    }

    @Test
    public void testNoCaptureScenarioBlack() throws NotMeException{
        testBoard.addPiece(blackRook, 4, 4);
        testBoard.addPiece(new Pawn(false), 1, 4);
        testBoard.addPiece(new Pawn(false), 6, 4);
        testBoard.addPiece(new Pawn(false), 4, 1);
        testBoard.addPiece(new Pawn(false), 4, 6);
        HashSet<Point> expectedSet = new HashSet<Point>();

        for(int i = 0; i < 8; i++){
            if(i != 4){
                expectedSet.add(new Point(i, 4));
                expectedSet.add(new Point(4, i));
            }
        }

        // Remove squares blocked by Pawns
        // as well as the Pawn squares themselves since you don't move in the
        // square of your team mate.
        expectedSet.remove(new Point(0, 4));
        expectedSet.remove(new Point(1, 4));
        expectedSet.remove(new Point(6, 4));
        expectedSet.remove(new Point(7, 4));
        expectedSet.remove(new Point(4, 0));
        expectedSet.remove(new Point(4, 1));
        expectedSet.remove(new Point(4, 6));
        expectedSet.remove(new Point(4, 7));

        Set<Point> actualMoves = blackRook.getMoves(4, 4, testBoard);
        Assert.assertEquals(expectedSet, actualMoves);
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();
    
        // Try to use a white rook to move the black rook at (0, 0).
        whiteRook.getMoves(0, 0, testBoard);
    }
}
