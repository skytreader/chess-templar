package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import net.skytreader.kode.chesstemplar.GameArbiter;

/**
Just does random moves. For illustrative purposes only.
*/
public class RandomEngine extends AIEngine{
    private Random randomChooser;
    
    public RandomEngine(GameArbiter ga){
        super(ga);
        randomChooser = new Random();
    }
    
    @Override
    public Point[] getMove(){
        LinkedList<Point[]> nextMoves = new LinkedList<Point[]>(arbiter.getAllLegalMoves());
        int randomMoveIndex = randomChooser.nextInt(nextMoves.size());
        return nextMoves.get(randomMoveIndex);
    }
}
