package net.skytreader.kode.chesstemplar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest{
    
    private Board board;

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
    }
}
