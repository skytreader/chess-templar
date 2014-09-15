package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.Pawn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BlankBoardTest{
    
    private BlankBoard board;

    @Before
    public void setUp(){
        board = new BlankBoard();
    }

    @Test
    public void testInitialState(){
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                Assert.assertNull(board.getPieceAt(row, col));
            }
        }
    }

    @Test
    public void testAddPieceWhite(){
        Pawn whitePawn = new Pawn(true);
        board.addPiece(whitePawn, 4, 4);
        Assert.assertEquals(whitePawn, board.getPieceAt(4, 4));
    }

    @Test
    public void testAddPieceBlack(){
        Pawn blackPawn = new Pawn(false);
        board.addPiece(blackPawn, 4, 4);
        Assert.assertEquals(blackPawn, board.getPieceAt(4, 4));
    }
}
