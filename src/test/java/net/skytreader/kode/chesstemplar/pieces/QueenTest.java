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
            Set<Point> black03 = blackQueen.getLegalMoves(0, 3, testBoard);
            Set<Point> white73 = whiteQueen.getLegalMoves(7, 3, testBoard);

            Assert.assertEquals(black03, emptySet);
            Assert.assertEquals(white73, emptySet);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing initial configuration.");
            nme.printStackTrace();
        }
    }

    /**
    This test asserts that a queens move is just the set union of the moves of a
    Rook and a Bishop.
    */
    @Test
    public void testCommonLegalMovesBlack(){
        try{
            BlankBoard testBoard = new BlankBoard();
            testBoard.addPiece(blackQueen, 4, 4);

            BlankBoard dummyBoard = new BlankBoard();
            Rook blackRook = new Rook(false);
            dummyBoard.addPiece(blackRook, 4, 4);
            Set<Point> rookMoves = blackRook.getLegalMoves(4, 4, dummyBoard);
            dummyBoard.removePiece(4, 4);
            Bishop blackBishop = new Bishop(false);
            dummyBoard.addPiece(blackBishop, 4, 4);
            Set<Point> bishopMoves = blackBishop.getLegalMoves(4, 4, dummyBoard);

            HashSet<Point> queenExpectedMoves = new HashSet<Point>();
            queenExpectedMoves.addAll(rookMoves);
            queenExpectedMoves.addAll(bishopMoves);

            Set<Point> queenMoves = blackQueen.getLegalMoves(4, 4, testBoard);
            Assert.assertEquals(queenExpectedMoves, queenMoves);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCommonLegalMovesWhite(){
        try{
            BlankBoard testBoard = new BlankBoard();
            testBoard.addPiece(whiteQueen, 4, 4);

            BlankBoard dummyBoard = new BlankBoard();
            Rook whiteRook = new Rook(false);
            dummyBoard.addPiece(whiteRook, 4, 4);
            Set<Point> rookMoves = whiteRook.getLegalMoves(4, 4, dummyBoard);
            dummyBoard.removePiece(4, 4);
            Bishop whiteBishop = new Bishop(false);
            dummyBoard.addPiece(whiteBishop, 4, 4);
            Set<Point> bishopMoves = whiteBishop.getLegalMoves(4, 4, dummyBoard);

            HashSet<Point> queenExpectedMoves = new HashSet<Point>();
            queenExpectedMoves.addAll(rookMoves);
            queenExpectedMoves.addAll(bishopMoves);

            Set<Point> queenMoves = whiteQueen.getLegalMoves(4, 4, testBoard);
            Assert.assertEquals(queenExpectedMoves, queenMoves);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
        Board testBoard = new GridBoard();
    
        // Try to use a white queen to move the black rook at (0, 0).
        whiteQueen.getLegalMoves(0, 0, testBoard);
    }
}
