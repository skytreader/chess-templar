package net.skytreader.kode.chesstemplar.core;

import java.util.Observable;
import java.util.Observer;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
An attack graph represents the attacking relationship between pieces in a
Chess board. The graph is directed and the nodes represent the pieces in the
game. An edge coming from node A to node B means that piece B is under attack
by piece A.

@author Chad Estioco
*/
public class AttackGraph implements Observer{
    
    private Board observedBoard;

    /**
    Construct an attack graph from the current configuration of the Board b.

    @param b
    */
    public AttackGraph(Board b){
        observedBoard = b;
        observedBoard.addObserver(this);
    }

    public void move(int r1, int c1, int r2, int c2){
    }

    @Override
    public void update(Observable o, Object arg){
        if(observedBoard.equals(o)){
            // Only do something if the update comes from the board we are
            // observing.
        }
    }
}
