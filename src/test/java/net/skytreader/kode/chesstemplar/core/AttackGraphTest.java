package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.BlankBoard;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
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
        // This is an additional
        testBoard.addPiece(new Bishop(false), 5, 1);
        testGraph = new AttackGraph(testBoard);
    }

    @Test
    public void testIsAttacking(){
        // Same color does not attack
        Assert.assertFalse(testGraph.isAttacking(new Point(2, 1), new Point(3, 0)));
        Assert.assertFalse(testGraph.isAttacking(new Point(5, 7), new Point(4, 6)));

        // Different color attacks
        Assert.assertTrue(testGraph.isAttacking(new Point(3, 1), new Point(2, 1)));
        Assert.assertTrue(testGraph.isAttacking(new Point(6, 1), new Point(0, 7)));

        // Reverse might not be true
        Assert.assertFalse(testGraph.isAttacking(new Point(0, 7), new Point(6, 1)));
    }
}
