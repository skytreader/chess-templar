package net.skytreader.kode.chesstemplar.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PieceValueTrackerTest{
    
    private PieceValueTracker pvTracker;
    private final float TEST_VALUE = 8.88f;

    @Before
    public void setUp(){
        pvTracker = new PieceValueTracker();
    }

    @Test
    public void testPawnVal(){
        pvTracker.setPawnVal(TEST_VALUE);
        Assert.assertEquals(pvTracker.getPawnVal(), TEST_VALUE, 0f);
    }

    @Test
    public void testKnightVal(){
        pvTracker.setKnightVal(TEST_VALUE);
        Assert.assertEquals(pvTracker.getKnightVal(), TEST_VALUE, 0f);
    }

    @Test
    public void testBishopVal(){
        pvTracker.setBishopVal(TEST_VALUE);
        Assert.assertEquals(pvTracker.getBishopVal(), TEST_VALUE, 0f);
    }

    @Test
    public void testRookVal(){
        pvTracker.setRookVal(TEST_VALUE);
        Assert.assertEquals(pvTracker.getRookVal(), TEST_VALUE, 0f);
    }

    @Test
    public void testQueenVal(){
        pvTracker.setQueenVal(TEST_VALUE);
        Assert.assertEquals(pvTracker.getQueenVal(), TEST_VALUE, 0f);
    }
}
