package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.skytreader.kode.chesstemplar.core.AttackGraph;

import net.skytreader.kode.chesstemplar.exceptions.NotMeException;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Rook;

/**
The GameArbiter imposes the rules of Chess. An arbiter is tied to a particular
game. You can't make an Arbiter judge on multiple boards at a time!

By design, the GameArbiter is the only class that should directly interact with
a Board instance. If other threads/classes will modify the board state, it might
cause the arbiter to make wrong calls on the game.

@author Chad Estioco
*/
public class GameArbiter{
    
    private Board board;
    private AttackGraph attackGraph;
    private boolean whiteKingChecked;
    private boolean whiteKingMoved;
    private boolean whiteKingsideRookMoved;
    private boolean whiteQueensideRookMoved;
    private boolean blackKingChecked;
    private boolean blackKingMoved;
    private boolean blackKingsideRookMoved;
    private boolean blackQueensideRookMoved;

    private boolean lastMoveWhite;
    
    private Point whiteKingPosition;
    private Point blackKingPosition;

    private List<MoveFilter> moveFilters;
    /**
    Going from 0 to n (so the most recent move made is at the tail of the list).
    The arrays have two Point elements, the first being the origin and the second
    being the destination.
    */
    private LinkedList<Point[]> moveList;

    // Use these for comparisons
    private static final King WHITE_KING = new King(true);
    private static final King BLACK_KING = new King(false);
    private static final Rook WHITE_ROOK = new Rook(true);
    private static final Rook BLACK_ROOK = new Rook(false);
    
    /**
    A move filter checks for specific conditions and removes Point objects from
    the given Set. The filter may assume that the given Set<Point> is a subset
    of the possible moves of the ChessPiece at (r, c).
    */
    private interface MoveFilter{
        public Set<Point> filter(ChessPiece cp, int r, int c, Set<Point> moves);
    }

    private class CastleFilter implements MoveFilter{
        @Override
        public Set<Point> filter(ChessPiece cp, int r, int c, Set<Point> moves){
            Set<Point> pieceMoves = moves;
            if(WHITE_KING.equals(cp)){
                if(!whiteKingMoved && !whiteKingsideRookMoved){
                    boolean kingSideClear = true;
                    for(int i = 5; i < 7; i++){
                        if(board.getPieceAt(7, i) != null){
                            kingSideClear = false;
                            break;
                        }
                    }

                    if(kingSideClear && !attackGraph.isAttacked(new Point(7, 5), false)){
                        pieceMoves.add(new Point(7, 6));
                    }
                }

                if(!whiteKingMoved && !whiteQueensideRookMoved){
                    boolean queenSideClear = true;
                    // Check if the queen side is clear
                    for(int i = 1; i < 4; i++){
                        if(board.getPieceAt(7, i) != null){
                            queenSideClear = false;
                            break;
                        }
                    }
    
                    if(queenSideClear && !attackGraph.isAttacked(new Point(7, 3), false)){
                        pieceMoves.add(new Point(7, 2));
                    }
                }
            } else if(BLACK_KING.equals(cp)){
                
                if(!blackKingMoved && !blackKingsideRookMoved){
                    // Check if the king side is clear
                    boolean kingSideClear = true;
                    for(int i = 5; i < 7; i++){
                        if(board.getPieceAt(0, i) != null){
                            kingSideClear = false;
                            break;
                        }
                    }

                    if(kingSideClear && !attackGraph.isAttacked(new Point(0, 5), true)){
                        pieceMoves.add(new Point(0, 6));
                    }
                }

                if(!blackKingMoved && !blackQueensideRookMoved){
                    boolean queenSideClear = true;
                    // Check if the queen side is clear
                    for(int i = 1; i < 4; i++){
                        if(board.getPieceAt(0, i) != null){
                            queenSideClear = false;
                            break;
                        }
                    }

                    if(queenSideClear && !attackGraph.isAttacked(new Point(0, 3), true)){
                        pieceMoves.add(new Point(0, 2));
                    }
                }
            }
            return pieceMoves;
        }

        @Override
        public String toString(){
            return this.getClass().getCanonicalName();
        }
    }

    /**
    This filter checks that everyone's top priority is their King's safety. That
    is, they do not allow discovered attacks on their King nor will they allow
    moves that leaves their King in check.
    */
    private class KingCheckFilter implements MoveFilter{
        @Override
        public Set<Point> filter(ChessPiece cp, int r, int c, Set<Point> moves){
            /*
            Make every move and see if it puts the King in check.
            */
            Set<Point> updatedMoves = new HashSet<Point>();
            boolean pieceColor = cp.isWhite();
            Point kingForChecking = pieceColor ? whiteKingPosition : blackKingPosition;

            for(Point p : moves){
                // In case this is a capture move
                ChessPiece prevOccupant = board.getPieceAt(p.x, p.y);
                board.move(r, c, p.x, p.y);
                if(!kingForChecking.equals(new Point(r, c)) && attackGraph.getAttackers(kingForChecking).isEmpty()){
                    updatedMoves.add(p);
                } else if(kingForChecking.equals(new Point(r, c)) && attackGraph.getAttackers(p).isEmpty()){
                    updatedMoves.add(p);
                }
                // Get the board back to its previous state
                board.move(p.x, p.y, r, c);
                board.addPiece(prevOccupant, p.x, p.y);
            }

            return updatedMoves;
        }

        @Override
        public String toString(){
            return this.getClass().getCanonicalName();
        }
    }
    
    private class EnPassantFilter implements MoveFilter{
        @Override
        public Set<Point> filter(ChessPiece cp, int r, int c, Set<Point> moves){
            int moveListSize = moveList.size();
            int crucialRow = cp.isWhite() ? 3 : 4;
            int opponentOriginTracker = cp.isWhite() ? -2 : 2;

            if(moveListSize >= 2){
                Point[] myLastMove = moveList.get(moveListSize - 2);
                Point[] opponentLastMove = moveList.get(moveListSize - 1);

                if(myLastMove[0] == null || myLastMove[1] == null ||
                  opponentLastMove[0] == null || opponentLastMove[1] == null){
                    return moves;
                }

                // If my last move was to row index 3 of a pawn
                ChessPiece myLastPiece = board.getPieceAt(myLastMove[1].x, myLastMove[1].y);

                if(myLastPiece == null){
                    return moves;
                }

                if(Pawn.PIECE_NAME.equals(myLastPiece.getPieceName()) &&
                  myLastMove[1].x == crucialRow){
                    // Check if opponent's last move was the special pawn two moves down
                    ChessPiece opponentLastPiece = board.getPieceAt(opponentLastMove[1].x,
                      opponentLastMove[1].y);
                    
                    if(Pawn.PIECE_NAME.equals(opponentLastPiece.getPieceName()) &&
                      opponentLastMove[0].x == (opponentLastMove[1].x + opponentOriginTracker)){
                        if(cp.isWhite()){
                            moves.add(new Point(myLastMove[1].x - 1, opponentLastMove[1].y));
                        } else{
                            moves.add(new Point(myLastMove[1].x + 1, opponentLastMove[1].y));
                        }
                    }
                }
            }

            return moves;
        }
    }

    public GameArbiter(Board b){
        board = b;
        attackGraph = new AttackGraph(board);
        whiteKingMoved = false;
        whiteKingsideRookMoved = false;
        whiteQueensideRookMoved = false;
        blackKingMoved = false;
        blackKingsideRookMoved = false;
        blackQueensideRookMoved = false;
        lastMoveWhite = false;

        // FIXME Assuming that the board is set to normal initial state
        // FIXME that is not always the case
        whiteKingPosition = new Point(7, 4);
        blackKingPosition = new Point(0, 4);

        whiteKingChecked = false;
        blackKingChecked = false;

        moveList = new LinkedList<Point[]>();
        Point[] initialMove = {null, null};
        moveList.add(initialMove);

        moveFilters = new LinkedList<MoveFilter>();
        moveFilters.add(new CastleFilter());
        moveFilters.add(new KingCheckFilter());
        moveFilters.add(new EnPassantFilter());
    }
    
    private void removeFilter(Class c){
        for(MoveFilter mf : moveFilters){
            if(mf.getClass().equals(c)){
                moveFilters.remove(mf);
            }
        }
    }

    /**
    Get the last move allowed by this arbiter (i.e., the last move passed to
    requestMove for which requestMove returned true). A Point array with two
    elements is returned. The first element describes the initial square in the
    last legal move with the second element descrives the terminal square in the
    last legal move. If no moves have been performed on the board so far (i.e.,
    initial game configuration), the elements of the array are both null.
    */
    public Point[] getLastMove(){
        return moveList.getLast();
    }

    public Board getBoard(){
        return board;
    }

    public boolean isWhiteKingChecked(){
        return whiteKingChecked;
    }

    public boolean isBlackKingChecked(){
        return blackKingChecked;
    }

    public boolean isEndgame(){
        Set<Point[]> emptySet = new HashSet<Point[]>();
        Set<Point[]> allLegalMoves = getAllLegalMoves();
        return emptySet.equals(allLegalMoves);
    }

    /**
    Get the legal moves for every piece in the board.
    */
    public Set<Point[]> getAllLegalMoves(){
        Set<Point[]> legalMoves = new HashSet<Point[]>();
        Set<Point> pieces = board.getPiecePositions();

        try{
            for(Point piecePos : pieces){
                ChessPiece cp = board.getPieceAt(piecePos.x, piecePos.y);
                Set<Point> pieceMoves = cp.getMoves(piecePos.x, piecePos.y, board);

                for(Point terminalSquare : pieceMoves){
                    if(isLegalMove(piecePos.x, piecePos.y, terminalSquare.x,
                      terminalSquare.y)){
                        Point[] pa = {piecePos, terminalSquare};
                        legalMoves.add(pa);
                    }
                }
            }
        } catch(NotMeException nme){
            nme.printStackTrace();
        }

        return legalMoves;
    }

    /**
    Edits the moveset returned by the ChessPiece to take Chess rules in
    consideration.

    Among the edits involved are removing moves that will expose the King to
    a check and, in the case of the King, add the possibility of castles.

    FIXME Cannot castle _through_ a check.
    */
    protected Set<Point> legalMovesFilter(ChessPiece cp, int r, int c) throws NotMeException{
        Set<Point> pieceMoves = cp.getMoves(r, c, board);

        for(MoveFilter mf : moveFilters){
            pieceMoves = mf.filter(cp, r, c, pieceMoves);
        }

        return pieceMoves;
    }

    /*
    Function to check that the move is white king castling.
    */
    private boolean isWhiteCastle(int r1, int c1, int r2, int c2){
        return (r1 == 7 && c1 == 4) && ((r2 == 7 && c2 == 6) || (r2 == 7 &&
          c2 == 2));
    }

    /*
    Function to check the the move is black king castling.
    */
    private boolean isBlackCastle(int r1, int c1, int r2, int c2){
        return (r1 == 0 && c1 == 4) && ((r2 == 0 && c2 == 6) || (r2 == 0 &&
          c2 == 2));
    }
    
    /**
    Checks if the move described is an en passant capture. This method assumes
    that the board state and the move hass already been validated but that the
    move <i>has not been enacted yet</i>.

    The main purpose of this method is to check whether we must remove a pawn
    captured en passant. Legality of the en passant move is for legalMovesFilter
    to check. This method has very shallow checking.
    */
    private boolean isEnPassant(int r1, int c1, int r2, int c2){
        ChessPiece cp = board.getPieceAt(r1, c1);
        Pawn opposingPawn = new Pawn(!cp.isWhite());

        ChessPiece capturePawn = board.getPieceAt(r1, c2);
        return capturePawn != null && opposingPawn.equals(capturePawn);
    }
    
    /**
    Assuming that the given color just moved, check if any of the Kings got
    checked.
    */
    private void setKingCheckFlags(boolean pieceColor) throws NotMeException{
        Set<Point> piecePositions = board.getPiecePositions();

        for(Point pos : piecePositions){
            ChessPiece posPiece = board.getPieceAt(pos.x, pos.y);
            Set<Point> pieceMoves = legalMovesFilter(posPiece, pos.x, pos.y);

            if(pieceColor){
                // Only black King can be checked
                blackKingChecked = pieceMoves.contains(blackKingPosition);
                if(blackKingChecked){
                    break;
                }
            } else{
                whiteKingChecked = pieceMoves.contains(whiteKingPosition);
                if(whiteKingChecked){
                    break;
                }
            }
        }
    }
    
    /**
    Toggle the flags we use for checking validity of castle move requests.
    */
    private void setCastleFlags(ChessPiece movedPiece, int fromRow, int fromCol){
        if(movedPiece.equals(GameArbiter.WHITE_KING)){
            whiteKingMoved = true;
        } else if(movedPiece.equals(GameArbiter.BLACK_KING)){
            blackKingMoved = true;
        } else if(movedPiece.equals(GameArbiter.WHITE_ROOK) && fromRow == 7 && fromCol == 7){
            whiteKingsideRookMoved = true;
        } else if(movedPiece.equals(GameArbiter.WHITE_ROOK) && fromRow == 7 && fromCol == 0){
            whiteQueensideRookMoved = true;
        } else if(movedPiece.equals(GameArbiter.BLACK_ROOK) && fromRow == 0 && fromCol == 7){
            blackKingsideRookMoved = true;
        } else if(movedPiece.equals(GameArbiter.BLACK_ROOK) && fromRow == 0 && fromCol == 0){
            blackQueensideRookMoved = true;
        }
    }
    
    /**
    Checks if the described move is possible and legal and enacts it on the Board
    if it is so. The move is described as (r1, c1) being the initial square and
    (r2, c2) being the terminal square. This method returns true if the move
    described is possible and legal and has been enacted succesfully on the given
    Board.

    Note that if other classes modify the Board held by this arbiter, discrepancies
    might occur causing the arbiter to make wrong decisions on the state of the
    game.

    The legality of the move takes into consideration whose turn is it to move.
    That is, you can't call requestMove two consecutive times on an r1, c1 tile
    holding a piece with the same color.

    To describe a castle move, move the King to its terminal position once the
    castle is done.

    @param r1
    @param c1
    @param r2
    @param c2
    @return true if the move described is possible and legal and has been enacted
      successfully on the given Board.
    */
    public boolean requestMove(int r1, int c1, int r2, int c2){
        // The move has been done if, after this call, (r2, c2) contains the piece
        // previously at (r1, c1).
        if(!isLegalMove(r1, c1, r2, c2)){
            return false;
        }
        ChessPiece cp1 = board.getPieceAt(r1, c1);
        // Cache some booleans
        boolean isWhiteKing = false;
        boolean isBlackKing = false;

        if(cp1.equals(WHITE_KING)){
            isWhiteKing = true;
        } else if(cp1.equals(BLACK_KING)){
            isBlackKing = true;
        }

        try{
            if(isEnPassant(r1, c1, r2, c2)){
                board.removePiece(r1, c2);
            }

            board.move(r1, c1, r2, c2);
    
            lastMoveWhite = cp1.isWhite();
    
            Point[] move = {new Point(r1, c1), new Point(r2, c2)};
            moveList.add(move);

            // Check if the piece moved was a King and if so, note properly.
            if(isWhiteKing){
                whiteKingPosition.setLocation(r2, c2);
            } else if(isBlackKing){
                blackKingPosition.setLocation(r2, c2);
            }

            // Check if, in this new position, any King is checked.
            // Set both to false for the meantime, just in case check has
            // been avoided
            whiteKingChecked = false;
            blackKingChecked = false;

            setKingCheckFlags(cp1.isWhite());

            // Check if the move is castling because there are actually two
            // moves to make there.
            if(isWhiteKing && isWhiteCastle(r1, c1, r2, c2)){
                if(c2 == King.KINGSIDE_CASTLE_COL){
                    // Move the kingside rook
                    board.move(7, 7, 7, 5);
                } else{
                    board.move(7, 0, 7, 3);
                }
            } else if(isBlackKing && isBlackCastle(r1, c1, r2, c2)){
                if(c2 == King.KINGSIDE_CASTLE_COL){
                    board.move(0, 7, 0, 5);
                } else{
                    board.move(0, 0, 0, 3);
                }
            }
            
            setCastleFlags(cp1, r1, c1);

            // Check if both players has castled, and remove castle filter if so
            if((whiteKingMoved && (whiteKingsideRookMoved || whiteQueensideRookMoved)) &&
              (blackKingMoved && (blackKingsideRookMoved || blackQueensideRookMoved))){
                removeFilter(CastleFilter.class);
            }

            return true;
        } catch(NotMeException nme){
            // Should not happen at all
            nme.printStackTrace();
            return false;
        }
    }
    
    /**
    Dedicated method for checking whether a move is legal <em>for the game's
    given turn</em>. <strong>Does not</strong> make any moves on the board or
    change the state of the game or of the GameArbiter in any way.
    
    The legality of the move takes into consideration whose turn is it to move.
    That is, you can't call requestMove two consecutive times on an r1, c1 tile
    holding a piece with the same color.

    @param r1
    @param c1
    @param r2
    @param c2
    @return true if the move described is possible and legal
    */
    private boolean isLegalMove(int r1, int c1, int r2, int c2){
        ChessPiece cp1 = board.getPieceAt(r1, c1);

        // Piece checks
        if(cp1 == null){
            return false;
        }

        // Check that consecutive moves from a given side does not happen.
        if((cp1.isWhite() && lastMoveWhite) || (!cp1.isWhite() &&
          !lastMoveWhite)){
            return false;
        }

        try{
            // Check that the destination is a legal move
            Set<Point> legalMoves = legalMovesFilter(cp1, r1, c1);
            return legalMoves.contains(new Point(r2, c2));
        } catch(NotMeException nme){
            // Should not happen at all
            nme.printStackTrace();
            return false;
        }
    }
}
