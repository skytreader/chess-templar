package net.skytreader.kode.chesstemplar.core;

import net.skytreader.kode.chesstemplar.BlankBoard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AttackGraphTest{
    
    private BlankBoard testBoard;
    private AttackGraph testGraph;

    @Before 
    public void setUp(){
        testBoard = new BlankBoard();
        // TODO Find a good configuration
        testGraph = new AttackGraph(testBoard);
    }
}
