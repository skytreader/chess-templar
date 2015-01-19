package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import net.skytreader.kode.chesstemplar.GameArbiter;

public abstract class AIEngine{
    
    private GameArbiter arbiter;

    public AIEngine(GameArbiter arbiter){
        this.arbiter = arbiter;
    }

    public abstract Point[] getMove();
}
