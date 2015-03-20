package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.Set;

import net.skytreader.kode.chesstemplar.BlankBoard;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Rook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackGraphTest{
    
    private BlankBoard testBoard;
    private AttackGraph testGraph;

    /**
    7R/8/1P1K4/Pr6/6k1/7p/1b6/8
    */
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
        testBoard.addPiece(new Bishop(false), 6, 1);
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

        // Both empty
        Assert.assertFalse(testGraph.isAttacking(new Point(0, 0), new Point(0, 1)));
        // Left empty
        Assert.assertFalse(testGraph.isAttacking(new Point(1, 1), new Point(2, 1)));
        // Right empty
        Assert.assertFalse(testGraph.isAttacking(new Point(2, 1), new Point(0, 1)));
    }

    @Test
    public void testNotAttacking(){
        Assert.assertFalse(testGraph.isAttacking(new Point(0, 7), new Point(1, 6)));
        Assert.assertFalse(testGraph.isAttacking(new Point(2, 1), new Point(1, 1)));
    }

    @Test
    public void testControl() throws NotMeException{
        Set<Point> allPiecePos = testBoard.getPiecePositions();

        for(Point pos : allPiecePos){
            ChessPiece cp = testBoard.getPieceAt(pos.x, pos.y);
            Set<Point> cpMoves = cp.getMoves(pos.x, pos.y, testBoard);

            for(Point pressured : cpMoves){
                Assert.assertTrue(testGraph.isAttacking(pos, pressured));
            }
        }
    }

    @Test
    public void testUpdate() throws NotMeException{
        // Make a move on the board and the graph should update accordingly.
        testBoard.move(3, 1, 3, 7);
        // Pawn at b6 is no longer attacked by anything at b5 (b5 is empty)
        Assert.assertFalse(testGraph.isAttacking(new Point(3, 1), new Point(2, 1)));
        // Rook at h8 is no longer attacking pawn at h3
        Assert.assertFalse(testGraph.isAttacking(new Point(0, 7), new Point(5, 7)));
        // Rooks are in mutual attack
        Assert.assertTrue(testGraph.isAttacking(new Point(0, 7), new Point(3, 7)));
        Assert.assertTrue(testGraph.isAttacking(new Point(3, 7), new Point(0, 7)));

        /*
        Make sure that the underlying representation was also updated properly.
        */
        Set<Point> allPiecePos = testBoard.getPiecePositions();

        for(Point pos : allPiecePos){
            ChessPiece cp = testBoard.getPieceAt(pos.x, pos.y);
            Set<Point> cpMoves = cp.getMoves(pos.x, pos.y, testBoard);

            for(Point pressured : cpMoves){
                Assert.assertTrue(testGraph.isAttacking(pos, pressured));
            }
        }
    }
}
