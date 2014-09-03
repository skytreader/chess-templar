package net.skytreader.kode.chesstemplar;

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

public class BoardTest{
    
    private Board board;

    private final ChessPiece[] BLACK_ARRANGEMENT = {new Rook(false), new Knight(false),
      new Bishop(false), new Queen(false), new King(false), new Bishop(false),
      new Knight(false), new Rook(false)};
      
    private final ChessPiece[] WHITE_ARRANGEMENT = {new Rook(true), new Knight(true),
      new Bishop(true), new Queen(true), new King(true), new Bishop(true),
      new Knight(true), new Rook(true)};

    @Before
    public void setUp(){
        board = new Board();
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
}
