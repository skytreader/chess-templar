package net.skytreader.kode.chesstemplar.core;

import java.awt.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import net.skytreader.kode.chesstemplar.Board;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
An attack graph represents the attacking relationship between the squares of a
Chess board. The graph is directed and the nodes represent the squares in the
board. An edge coming from node A to node B means that the piece at node A is
in control/attacking square node B (regardless of whether there is a piece
in square node B or not).

In constructing the connections of the attack graph, the legality of moves is not
considered. It is still up to GameArbiter to filter the legality of moves. All
that is taken into account when constructing and using the attack graph is that
a piece <em>can</em> make the said move.

Any changes in the board is automatically reflected in AttackGraph.

@author Chad Estioco
*/
public class AttackGraph implements Observer{
    
    private Board observedBoard;
    /**
    The first element (index 0) of every list in this set-of-lists is the chess
    piece represented by that list. The rest of the elements in the list
    (elements 1 to n) are the nodes attacked by current element.
    */
    private Map<Point, Set<Point>> attackGraph;

    /**
    Construct an attack graph from the current configuration of the Board b.

    @param b
    */
    public AttackGraph(Board b){
        observedBoard = b;
        observedBoard.addObserver(this);
        attackGraph = new HashMap<Point, Set<Point>>();
        initializeAttackGraph();
    }
    
    /**
    Return true if the piece at square p1 is attacking square p2.
    If there is no piece at p1, return false.
    */
    public boolean isAttacking(Point p1, Point p2){
        try{
            ChessPiece cp1 = observedBoard.getPieceAt(p1.x, p1.y);
            ChessPiece cp2 = observedBoard.getPieceAt(p2.x, p2.y);
            if(cp1 == null){
                return false;
            }
            Set<Point> cp1Moves = cp1.getMoves(p1.x, p1.y, observedBoard);
            return cp1Moves.contains(p2);
        } catch(NotMeException nme){
            // Shouldn't get here
            nme.printStackTrace();
            return false;
        }
    }

    /**
    Get the location of the pieces exerting pressure on square p1.
    */
    public Set<Point> getAttackers(Point p1){
        Set<Point> attackers = new HashSet<Point>();
        for(int i = 0; i < Board.BOARD_WIDTH; i++){
            for(int j = 0; j < Board.BOARD_HEIGHT; j++){
                Point possibleAttacker = new Point(i, j);
                if(isAttacking(possibleAttacker, p1)){
                    attackers.add(possibleAttacker);
                }
            }
        }
        return attackers;
    }
    
    /**
    Check whether the given Point p is under attack by any piece of the given
    color.
    */
    public boolean isAttacked(Point p, boolean color){
        Set<Point> attackers = getAttackers(p);

        for(Point attacker : attackers){
            ChessPiece piece = observedBoard.getPieceAt(attacker.x, attacker.y);
            if(piece.isWhite() == color){
                return true;
            }
        }

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
                possibleMoves.add(pos);
                Set<Point> cpNode = new HashSet<Point>();
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
                attackGraph.put(pos, possibleMoves);
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
        Set<Point> theAttacked = attackGraph.get(attacker);

        if(theAttacked == null){
            theAttacked = new HashSet<Point>();
        }

        theAttacked.add(target);
        attackGraph.put(attacker, theAttacked);
    }

    @Override
    public void update(Observable o, Object arg){
        if(observedBoard.equals(o)){
            // Only do something if the update comes from the board we are
            // observing.

            /*
            Get the source square of the move and remove it from the "attacked"
            list of everyone.
            */
            Point[] moveDesc = (Point[]) arg;

            for(Point square : attackGraph.keySet()){
                Set<Point> attackedSet = attackGraph.get(square);
                attackedSet.remove(moveDesc[0]);
                attackGraph.put(square, attackedSet);
            }

            /*
            Get the terminal square of the move and add it to its new attackers
            and create link it to those it is attacking.
            */
            Set<Point> allPiecePos = observedBoard.getPiecePositions();
            try{
                ChessPiece movedPiece = observedBoard.getPieceAt(moveDesc[1].x,
                  moveDesc[1].y);
                Set<Point> movedPieceMoves = movedPiece.getMoves(moveDesc[1].x,
                  moveDesc[1].y, observedBoard);

                for(Point pos : allPiecePos){
                    ChessPiece cp = observedBoard.getPieceAt(pos.x, pos.y);
                    Set<Point> moves = cp.getMoves(pos.x, pos.y, observedBoard);
                    
                    if(moves.contains(moveDesc[1])){
                        attackGraph.put(pos, moves);
                    }

                    /*if(movedPieceMoves.contains(pos)){
                        updateAttackGraph(moveDesc[1], pos);
                    }*/
                }

                for(Point pos : movedPieceMoves){
                    updateAttackGraph(moveDesc[1], pos);
                }
            } catch(NotMeException nme){
                // Again, code should never go here
                nme.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        Set<Point> attackers = attackGraph.keySet();
        StringBuilder sb = new StringBuilder("attackGraph: ");

        for(Point a : attackers){
            sb.append(a.toString());
            sb.append(" attacks ");
            sb.append(attackGraph.get(a).toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
