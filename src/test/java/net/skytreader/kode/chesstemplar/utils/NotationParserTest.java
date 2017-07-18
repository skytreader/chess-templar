package net.skytreader.kode.chesstemplar.utils;

import org.junit.Assert;
import org.junit.Test;

public class NotationParserTest{
    
    @Test
    public void testValiditySimpleMoves(){
        Assert.assertTrue(NotationParser.isValid("Be8"));
        Assert.assertTrue(NotationParser.isValid("e4"));
    }

    @Test
    public void testValidityCaptures(){
        Assert.assertTrue(NotationParser.isValid("Nxb5"));
        Assert.assertTrue(NotationParser.isValid("exb5"));
    }

    @Test
    public void testValidityDisambiguation(){
        Assert.assertTrue(NotationParser.isValid("Ngf3"));
        Assert.assertTrue(NotationParser.isValid("Ndf3"));
        Assert.assertTrue(NotationParser.isValid("N5f3"));
        Assert.assertTrue(NotationParser.isValid("N1f3"));
    }

    @Test
    public void testValidityPawnPromotion(){
        Assert.assertTrue(NotationParser.isValid("e8Q"));
    }

    @Test
    public void testValidityCastling(){
        Assert.assertTrue(NotationParser.isValid("0-0"));
        Assert.assertTrue(NotationParser.isValid("0-0-0"));
    }

    @Test
    public void testValidityCheck(){
        // Normal and capture moves.
        Assert.assertTrue(NotationParser.isValid("Nb4+"));
        Assert.assertTrue(NotationParser.isValid("Nb4++"));
        Assert.assertTrue(NotationParser.isValid("Nxb4+"));
        Assert.assertTrue(NotationParser.isValid("Nxb4++"));

        // Castle checks opponent.
        Assert.assertTrue(NotationParser.isValid("0-0+"));
        Assert.assertTrue(NotationParser.isValid("0-0++"));
        Assert.assertTrue(NotationParser.isValid("0-0-0+"));
        Assert.assertTrue(NotationParser.isValid("0-0-0++"));

        // Pawn promotion checks.
        Assert.assertTrue(NotationParser.isValid("e8Q+"));
        Assert.assertTrue(NotationParser.isValid("e8Q++"));

        //Disambiguation checks.
        Assert.assertTrue(NotationParser.isValid("Ngf3+"));
        Assert.assertTrue(NotationParser.isValid("Ngf3++"));
    }

}
