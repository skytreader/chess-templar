package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.HashSet;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;
import net.skytreader.kode.chesstemplar.GameArbiter;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

public abstract class AIEngine{
    
    protected GameArbiter arbiter;

    public AIEngine(GameArbiter arbiter){
        this.arbiter = arbiter;
    }

    public abstract Point[] getMove();
}
