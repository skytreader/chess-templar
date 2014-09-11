package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.Arrays;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Knight;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Queen;
import net.skytreader.kode.chesstemplar.pieces.Rook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GridBoardTest{
    
    private GridBoard board;

    private final ChessPiece[] BLACK_ARRANGEMENT = {new Rook(false), new Knight(false),
      new Bishop(false), new Queen(false), new King(false), new Bishop(false),
      new Knight(false), new Rook(false)};
      
    private final ChessPiece[] WHITE_ARRANGEMENT = {new Rook(true), new Knight(true),
      new Bishop(true), new Queen(true), new King(true), new Bishop(true),
      new Knight(true), new Rook(true)};

    @Before
    public void setUp(){
        board = new GridBoard();
    }
    
    /**
    The assumption with this test is that the setUp method does naught but
    initialize the board; we are fresh off from `new Board()`.
    */
    @Test
    public void testInitialState(){
        ChessPiece blackPawn = new Pawn(false);
        ChessPiece whitePawn = new Pawn(true);

        // Row 0 at every column, the BLACK_ARRANGEMENT
        for(int i = 0; i < 8; i++){
            Assert.assertEquals(board.getPieceAt(0, i), BLACK_ARRANGEMENT[i]);
        }

        // Row 1 at every column, should be blackPawn
        for(int i = 0; i < 8; i++){
            Assert.assertEquals(board.getPieceAt(1, i), blackPawn);
        }

        // Row 6 at every column, should be whitePawn
        for(int i = 0; i < 8; i++){
            Assert.assertEquals(board.getPieceAt(6, i), whitePawn);
        }

        // Row 7 at every column, should be WHITE_aRRANGEMENT
        for(int i = 0; i < 8; i++){
            Assert.assertEquals(board.getPieceAt(7, i), WHITE_ARRANGEMENT[i]);
        }
    }

    /**
    Test that, upon construction, when the board is at its initial state, the
    tiles at the middle is blank.
    */
    @Test
    public void testBlankTiles(){
        for(int row = 2; row < 6; row++){
            for(int col = 0; col < 8; col++){
                Assert.assertEquals(board.getPieceAt(row, col), null);
            }
        }
    }
    
    /**
    Test everything related to setting and getting pieces around the board.
    */
    @Test
    public void testMovements(){
        // Move a black pawn
        ChessPiece blackPawn = board.getPieceAt(1, 0);
        board.move(1, 0, 2, 0);
        Assert.assertEquals(board.getPieceAt(2, 0), blackPawn);

        // Assert that null is returned for off-range indices
        ChessPiece negRowPiece = board.getPieceAt(-1, 0);
        ChessPiece negColPiece = board.getPieceAt(0, -1);

        Assert.assertTrue(negRowPiece == null);
        Assert.assertTrue(negColPiece == null);
    }

    @Test
    public void testGetLastMove(){
        Point from1 = new Point(1, 0);
        Point to1 = new Point(3, 0);
        Point[] expected1 = {from1, to1};
        board.move(1, 0, 3, 0);
        Assert.assertTrue(Arrays.equals(expected1, board.getLastMove()));

        Point from2 = new Point(6, 0);
        Point to2 = new Point(4, 0);
        Point[] expected2 = {from2, to2};
        board.move(6, 0, 4, 0);
        Assert.assertTrue(Arrays.equals(expected2, board.getLastMove()));
    }

    @Test
    public void testRemovePiece(){
        Board testBoard = new GridBoard();
        Assert.assertNotNull(testBoard.getPieceAt(0, 0));
        testBoard.removePiece(0, 0);
        Assert.assertNull(testBoard.getPieceAt(0, 0));
    }

}
