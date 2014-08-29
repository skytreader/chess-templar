package net.skytreader.kode.chesstemplar.pieces;

import net.skytreader.kode.chesstemplar.Board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PawnTest{
    
    private Pawn whitePawn;
    private Pawn blackPawn;
    
    @Before
    public void setUp(){
        whitePawn = new Pawn(ChessPiece.WHITE);
        blackPawn = new Pawn(!ChessPiece.WHITE);
    }

    @Test
    public void testHashCode(){
        Assert.assertNotEquals(whitePawn.hashCode(), blackPawn.hashCode());
    }

    @Test
    public void testEquals(){
        Pawn anotherWhite = new Pawn(ChessPiece.WHITE);
        Assert.assertTrue(whitePawn.equals(whitePawn));
        boolean forSymmetry = whitePawn.equals(anotherWhite);
        Assert.assertTrue(forSymmetry);

        if(forSymmetry){
            Assert.assertTrue(anotherWhite.equals(whitePawn));
        }

        Pawn thirdWhite = new Pawn(ChessPiece.WHITE);
        boolean forTransitivity = whitePawn.equals(thirdWhite);
        Assert.assertTrue(forTransitivity);

        if(forTransitivity){
            Assert.assertTrue(whitePawn.equals(anotherWhite));
            Assert.assertTrue(anotherWhite.equals(thirdWhite));
        }
    }
}
