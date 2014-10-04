package net.skytreader.kode.chesstemplar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameArbiterTest{
    
    private GridBoard concreteBoard;
    private BlankBoard freeBoard;
    private GameArbiter rigidArbiter;
    private GameArbiter freeFormArbiter;

    @Before
    public void setUp(){
        concreteBoard = new GridBoard();
        freeBoard = new BlankBoard();
        rigidArbiter = new GameArbiter(concreteBoard);
        freeFormArbiter = new GameArbiter(freeBoard);
    }

    @Test
    public void testConcreteInitialState(){
        Assert.assertFalse(rigidArbiter.isEndgame());
        Assert.assertFalse(rigidArbiter.canWhiteKingCastle());
        Assert.assertFalse(rigidArbiter.canBlackKingCastle());
    }

    @Test
    public void testContrivedKingsideCastleScenarioWhite(){
        concreteBoard.removePiece(7, 5);
        concreteBoard.removePiece(7, 6);
        Assert.assertTrue(rigidArbiter.canWhiteKingCastle());
    }

    @Test
    public void testContrivedQueensideCastleScenarioWhite(){
        concreteBoard.removePiece(7, 3);
        concreteBoard.removePiece(7, 2);
        concreteBoard.removePiece(7, 1);
        Assert.assertTrue(rigidArbiter.canWhiteKingCastle());
    }
}
