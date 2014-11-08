package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
An attack graph represents the attacking relationship between pieces in a
Chess board. The graph is directed and the nodes represent the pieces in the
game. An edge coming from node A to node B means that piece B is under attack
by piece A.

In constructing the connections of the attack graph, the legality of moves is not
considered. It is still up to GameArbiter to filter the legality of moves. All
that is taken into account when constructing and using the attack graph is that
a piece <em>can</em> make the said move.

@author Chad Estioco
*/
public class AttackGraph implements Observer{
    
    private Board observedBoard;
    /**
    The first element (index 0) of every list in this list-of-lists is the chess
    piece represented by that list. The rest of the elements in the list
    (elements 1 to n) are the nodes attacked by current element.
    */
    private List<List<Point>> attackGraph;

    /**
    Construct an attack graph from the current configuration of the Board b.

    @param b
    */
    public AttackGraph(Board b){
        observedBoard = b;
        observedBoard.addObserver(this);
        attackGraph = new LinkedList<List<Point>>();
        initializeAttackGraph();
    }
    
    /**
    Create the actual attack graph from the observed board.
    */
    private void initializeAttackGraph(){
        Set<Point> piecePos = observedBoard.getPiecePositions();

        // (1) Get all the positions of all pieces
        Set<Point> whitePieces = new HashSet<Point>();
        Set<Point> blackPieces = new HashSet<Point>();

        for(Point pos : piecePos){
            ChessPiece cp = observedBoard.getPieceAt(pos.x, pos.y);

            if(cp.isWhite()){
                whitePieces.add(pos);
            } else{
                blackPieces.add(pos);
            }
        }

        /*
        (2) Construct the attack graph from the following definition:

        Let piece A and piece B be pieces of different colors. piece A is
        attacking piece B if the position of piece B is in the moveset of piece
        A.
        */
        for(Point pos : piecePos){
            ChessPiece cp = observedBoard.getPieceAt(pos.x, pos.y);
            Set<Point> possibleMoves = cp.getMoves(pos.x, pos.y, observedBoard);
            List<Point> cpNode = new LinkedList<Point>();
            cpNode.add(pos);

            if(cp.isWhite()){
                // Search the black pieces
                for(Point blackPiece : blackPieces){
                    if(possibleMoves.contains(blackPiece)){
                        cpNode.add(blackPiece);
                    }
                }
            } else{
                // Search the white pieces
                for(Point whitePiece : whitePieces){
                    if(possibleMoves.contains(whitePiece)){
                        cpNode.add(whitePiece);
                    }
                }
            }
            attackGraph.add(cpNode);
        }
    }

    @Override
    public void update(Observable o, Object arg){
        if(observedBoard.equals(o) && observedBoard.hasChanged()){
            // Only do something if the update comes from the board we are
            // observing.
        }
    }
}
