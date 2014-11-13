package net.skytreader.kode.chesstemplar.core;

import net.skytreader.kode.chesstemplar.BlankBoard;

import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Rook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackGraphTest{
    
    private BlankBoard testBoard;
    private AttackGraph testGraph;

    @Before 
    public void setUp(){
        testBoard = new BlankBoard();
        // Derived from (Fenton vs. Potter, 1875) with a few additional pieces
        testBoard.addPiece(new Rook(true), 0, 7);
        testBoard.addPiece(new Pawn(true), 2, 1);
        testBoard.addPiece(new King(true), 2, 3);
        testBoard.addPiece(new Pawn(true), 3, 0);
        testBoard.addPiece(new Rook(false), 3, 1);
        testBoard.addPiece(new King(false), 4, 6);
        testBoard.addPiece(new Pawn(false), 5, 7);
        testGraph = new AttackGraph(testBoard);
    }
}
