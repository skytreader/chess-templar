package net.skytreader.kode.chesstemplar.pieces;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

public class QueenTest{
    
    private Queen whiteQueen;
    private Queen blackQueen;
    private BlankBoard testBoard;
    private GridBoard gridBoard;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp(){
        whiteQueen = new Queen(ChessPiece.WHITE);
        blackQueen = new Queen(!ChessPiece.WHITE);
        testBoard = new BlankBoard();
        gridBoard = new GridBoard();
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
            Board gridBoard = new GridBoard();
            HashSet<Point> emptySet = new HashSet<Point>();
            Set<Point> black03 = blackQueen.getMoves(0, 3, gridBoard);
            Set<Point> white73 = whiteQueen.getMoves(7, 3, gridBoard);

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
    public void testCommonMovesBlack(){
        try{
            testBoard.addPiece(blackQueen, 4, 4);
            Set<Point> queenExpectedMoves = getExpectedMoves(4, 4, false,
              new HashMap<Point, ChessPiece>());
            Set<Point> queenMoves = blackQueen.getMoves(4, 4, testBoard);
            Assert.assertEquals(queenExpectedMoves, queenMoves);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    /**
    @param r
    @param c
    @param color
    @param piecePos
    */
    private Set<Point> getExpectedMoves(int r, int c, boolean color,
      Map<Point, ChessPiece> piecePos){
        try{
            BlankBoard dummyBoard = new BlankBoard();

            for(Point p : piecePos.keySet()){
                dummyBoard.addPiece(piecePos.get(p), p.x, p.y);
            }

            Rook rook = new Rook(color);
            dummyBoard.addPiece(rook, r, c);
            Set<Point> rookMoves = rook.getMoves(r, c, dummyBoard);
            dummyBoard.removePiece(r, c);
            Bishop bishop = new Bishop(color);
            dummyBoard.addPiece(bishop, r, c);
            Set<Point> bishopMoves = bishop.getMoves(4, 4, dummyBoard);

            HashSet<Point> queenExpectedMoves = new HashSet<Point>();
            queenExpectedMoves.addAll(rookMoves);
            queenExpectedMoves.addAll(bishopMoves);

            return queenExpectedMoves;
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
            return null;
        }
    }

    @Test
    public void testCommonMovesWhite(){
        try{
            testBoard.addPiece(whiteQueen, 4, 4);

            Set<Point> queenExpectedMoves = getExpectedMoves(4, 4, true, new HashMap<Point, ChessPiece>());

            Set<Point> queenMoves = whiteQueen.getMoves(4, 4, testBoard);
            Assert.assertEquals(queenExpectedMoves, queenMoves);
        } catch(NotMeException nme){
            Assert.fail("NotMeException thrown while testing common legal moves.");
            nme.printStackTrace();
        }
    }

    @Test
    public void testCaptureScenarioWhite() throws NotMeException{
        testBoard.addPiece(whiteQueen, 4, 4);
        Map<Point, ChessPiece> piecePos = new HashMap<Point, ChessPiece>();
        piecePos.put(new Point(1, 4), new Pawn(false));
        testBoard.addPiece(new Pawn(false), 1, 4);
        Set<Point> queenExpectedMoves = getExpectedMoves(4, 4, true, piecePos);
        Set<Point> queenMoves = whiteQueen.getMoves(4, 4, testBoard);

        Assert.assertEquals(queenExpectedMoves, queenMoves);
    }

    @Test
    public void testCaptureScenarioBlack() throws NotMeException{
        testBoard.addPiece(blackQueen, 4, 4);
        Map<Point, ChessPiece> piecePos = new HashMap<Point, ChessPiece>();
        piecePos.put(new Point(1, 4), new Pawn(true));
        testBoard.addPiece(new Pawn(false), 1, 4);
        Set<Point> queenExpectedMoves = getExpectedMoves(4, 4, true, piecePos);
        Set<Point> queenMoves = blackQueen.getMoves(4, 4, testBoard);

        Assert.assertEquals(queenExpectedMoves, queenMoves);
    }

    @Test
    public void testNotMe() throws NotMeException{
        exception.expect(NotMeException.class);
    
        // Try to use a white queen to move the black rook at (0, 0).
        whiteQueen.getMoves(0, 0, gridBoard);
    }
}
