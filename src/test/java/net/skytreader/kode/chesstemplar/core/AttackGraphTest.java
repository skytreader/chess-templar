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
    8/1b6/7p/6k1/Pr6/1P1K4/8/7R
    */
    @Before 
    public void setUp(){
        testBoard = new BlankBoard();
        // Derived from (Fenton vs. Potter, 1875) with a few additional pieces
        testBoard.addPiece(new Rook(true), 7, 7);
        testBoard.addPiece(new Pawn(true), 5, 1);
        testBoard.addPiece(new King(true), 5, 3);
        testBoard.addPiece(new Pawn(true), 4, 0);
        testBoard.addPiece(new Rook(false), 4, 1);
        testBoard.addPiece(new King(false), 3, 6);
        testBoard.addPiece(new Pawn(false), 2, 7);
        // This is an additional
        testBoard.addPiece(new Bishop(false), 1, 1);
        testGraph = new AttackGraph(testBoard);
    }

    @Test
    public void testIsAttacking(){
        // Same color does not attack
        Assert.assertFalse(testGraph.isAttacking(new Point(5, 1), new Point(4, 0)));
        Assert.assertFalse(testGraph.isAttacking(new Point(2, 7), new Point(3, 6)));

        // Different color attacks
        Assert.assertTrue(testGraph.isAttacking(new Point(4, 1), new Point(5, 1)));
        Assert.assertTrue(testGraph.isAttacking(new Point(1, 1), new Point(7, 7)));

        // Reverse might not be true
        Assert.assertFalse(testGraph.isAttacking(new Point(7, 7), new Point(1, 1)));

        // Both empty
        Assert.assertFalse(testGraph.isAttacking(new Point(0, 0), new Point(0, 1)));
        // Right empty
        Assert.assertFalse(testGraph.isAttacking(new Point(1, 1), new Point(5, 1)));
        // Left empty
        Assert.assertFalse(testGraph.isAttacking(new Point(5, 1), new Point(0, 1)));
    }

    @Test
    public void testNotAttacking(){
        Assert.assertFalse(testGraph.isAttacking(new Point(7, 7), new Point(1, 6)));
        Assert.assertFalse(testGraph.isAttacking(new Point(5, 1), new Point(6, 1)));
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
        testBoard.move(4, 1, 4, 7);
        // Pawn at b3 is no longer attacked by anything at b5 (b5 is empty)
        Assert.assertFalse(testGraph.isAttacking(new Point(4, 1), new Point(5, 1)));
        // Rook at h8 is no longer attacking pawn at h3
        Assert.assertFalse(testGraph.isAttacking(new Point(7, 7), new Point(2, 7)));
        // Rooks are in mutual attack
        Assert.assertTrue(testGraph.isAttacking(new Point(7, 7), new Point(4, 7)));
        Assert.assertTrue(testGraph.isAttacking(new Point(4, 7), new Point(7, 7)));

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
