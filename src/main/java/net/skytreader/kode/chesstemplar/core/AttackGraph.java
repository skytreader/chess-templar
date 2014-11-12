package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

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
    The first element (index 0) of every list in this set-of-lists is the chess
    piece represented by that list. The rest of the elements in the list
    (elements 1 to n) are the nodes attacked by current element.

    TODO We'll get better performance if we change this to
    </code>Map<Point, List<Point>></code>
    */
    private Set<List<Point>> attackGraph;

    /**
    Construct an attack graph from the current configuration of the Board b.

    @param b
    */
    public AttackGraph(Board b){
        observedBoard = b;
        observedBoard.addObserver(this);
        attackGraph = new HashSet<List<Point>>();
        initializeAttackGraph();
    }
    
    /**
    Return true if the piece at square p1 is attacking the piece at square p2.
    If either p1 or p2 is empty, return false.
    */
    public boolean isAttacking(Point p1, Point p2){
        return false;
    }
    
    /**
    Create the actual attack graph from the observed board.
    */
    private void initializeAttackGraph(){
        try{
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
        } catch(NotMeException nme){
            // There should be no reason for the code above to go here.
            nme.printStackTrace();
        }
    }
    
    /**
    Update the attack graph and add target to those who are attacked by attacker.

    Assumptions:
      - attacker and target are not equal
    */
    private void updateAttackGraph(Point attacker, Point target){
        for(List<Point> attackList : attackGraph){
            if(attackList.get(0).equals(attacker) && !attackList.contains(target)){
                attackGraph.remove(attackList);
                attackList.add(target);
                attackGraph.add(attackList);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg){
        if(observedBoard.equals(o) && observedBoard.hasChanged()){
            // Only do something if the update comes from the board we are
            // observing.

            /*
            Get the source square of the move and remove it from the "attacked"
            list of everyone.
            */
            Point[] moveDesc = (Point[]) arg;

            for(List<Point> attackList : attackGraph){
                // Remove the element from the Set first because we _might_ need
                // to modify it.
                attackGraph.remove(attackList);
                if(!attackList.get(0).equals(moveDesc[0]) &&
                  attackList.contains(moveDesc[0])){
                    attackList.remove(moveDesc[0]);
                }
                // Add back, now that possible modifications are done
                attackGraph.add(attackList);
            }

            /*
            Get the terminal square of the move and add it to its new attackers
            */
            Set<Point> piecePos = observedBoard.getPiecePositions();
            try{
                for(Point pos : piecePos){
                    ChessPiece cp = observedBoard.getPieceAt(pos.x, pos.y);
                    Set<Point> moves = cp.getMoves(pos.x, pos.y, observedBoard);
                    
                    if(moves.contains(moveDesc[1])){
                        updateAttackGraph(pos, moveDesc[1]);
                    }
                }
            } catch(NotMeException nme){
                // Again, code should never go here
                nme.printStackTrace();
            }

            /*
            Finally, note the pieces the moved piece attacks in its new position
            */
            try{
                ChessPiece movedPiece = observedBoard.getPieceAt(moveDesc[1].x,
                  moveDesc[1].y);
                Set<Point> movedPieceMoves = movedPiece.getMoves(moveDesc[1].x,
                  moveDesc[1].y, observedBoard);
                for(Point pos : piecePos){
                    if(movedPieceMoves.contains(pos)){
                        updateAttackGraph(moveDesc[1], pos);
                    }
                }
            } catch(NotMeException nme){
                // TODO Maybe, merge this with the try-catch block above since
                // I'd have the same comments on this one. Maybe even just do
                // everything in one loop.
                nme.printStackTrace();
            }
        }
    }
}
