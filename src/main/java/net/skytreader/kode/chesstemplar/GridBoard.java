package net.skytreader.kode.chesstemplar;

import java.awt.Point;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Knight;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Queen;
import net.skytreader.kode.chesstemplar.pieces.Rook;

/**
Memory wasteful implementation of Board as a grid.

@author Chad Estioco
*/
public class GridBoard extends Board{
    protected ChessPiece[] whitePieces = new ChessPiece[16];
    protected ChessPiece[] blackPieces = new ChessPiece[16];
    /**
    Convention: Unoccuppied squares contain negative integers, while occupied
    squares contain positive integers. Let w be the index of a white piece in
    array whitePieces. Then, its position in the board is represented by the
    cell containing the integer
        f(w) = 2w

    Let b be the index of a black piece in array blackPieces. Then, its position
    in the board is represented by the cell containing the integer
        f(b) = 2b + 1
    */
    protected int[][] board = new int[8][8];

    // FIXME Review why do we need this again?
    private final List<ChessPiece> WHITE_LIST;
    private final List<ChessPiece> BLACK_LIST;

    private final ChessPiece[] BLACK_ARRANGEMENT = {new Rook(false), new Knight(false),
      new Bishop(false), new Queen(false), new King(false), new Bishop(false),
      new Knight(false), new Rook(false)};
      
    private final ChessPiece[] WHITE_ARRANGEMENT = {new Rook(true), new Knight(true),
      new Bishop(true), new Queen(true), new King(true), new Bishop(true),
      new Knight(true), new Rook(true)};

    private Point lastSrc;
    private Point lastDest;

    /**
    Upon construction of a GameBoard, all the pieces are in their proper initial
    positions, ready for play.
    */
    public GridBoard(){
        generateBlackPieces();

        for(int i = 0; i < 8; i++){
            board[0][i] = getBlackRep(i);
        }

        for(int i = 8; i < 16; i++){
            board[1][i - 8] = getBlackRep(i);
        }

        generateWhitePieces();

        for(int i = 0; i < 8; i++){
            // Add 8 because these are pawns.
            board[6][i] = getWhiteRep(i + 8);
        }

        for(int i = 8; i < 16; i++){
            // Subtract 8 because this is the main pieces.
            board[7][i - 8] = getWhiteRep(i - 8);
        }

        for(int row = 2; row < 6; row++){
            for(int col = 0; col < 8; col++){
                board[row][col] = -1;
            }
        }

        WHITE_LIST = Arrays.asList(whitePieces);
        BLACK_LIST = Arrays.asList(blackPieces);
    }
    
    /**
    Given the index of a piece in whitePieces, return its representation in
    the board.
    */
    protected int getWhiteRep(int ai){
        return 2 * ai;
    }

    /**
    Given the index of a piece in blackPieces, return its representation
    in the board.
    */
    protected int getBlackRep(int ai){
        return (2 * ai) + 1;
    }

    /**
    Given the representation of a whitePiece, return its index in whitePieces.
    */
    protected int getWhiteIndex(int rep){
        return rep / 2;
    }

    /**
    Given the representation of a blackPiece, return its index in blackPieces.
    */
    protected int getBlackIndex(int rep){
        return (rep - 1) / 2;
    }
    
    /**
    Instantiates white pieces into the whitePieces array. The order is
    guaranteed to be as follows:
        indices 0 - 7 are the main pieces in starting order with Queenside Rook
        at 0 and Kingside Rook at index 7;
        indices 8 - 15 are the pawns
    */
    private void generateWhitePieces(){
        whitePieces[0] = new Rook(true);
        whitePieces[1] = new Knight(true);
        whitePieces[2] = new Bishop(true);
        whitePieces[3] = new Queen(true);
        whitePieces[4] = new King(true);
        whitePieces[5] = new Bishop(true);
        whitePieces[6] = new Knight(true);
        whitePieces[7] = new Rook(true);
        whitePieces[8] = new Pawn(true);
        whitePieces[9] = new Pawn(true);
        whitePieces[10] = new Pawn(true);
        whitePieces[11] = new Pawn(true);
        whitePieces[12] = new Pawn(true);
        whitePieces[13] = new Pawn(true);
        whitePieces[14] = new Pawn(true);
        whitePieces[15] = new Pawn(true);
    }

    /**
    Instantiates black pieces into the blackPieces array. The order is
    guaranteed to be as follows:
        indices 0 - 7 are the main pieces in starting order with Queenside Rook
        at 0 and Kingside Rook at index 7;
        indices 8 - 15 are the pawns
    */
    private void generateBlackPieces(){
        blackPieces[0] = new Rook(false);
        blackPieces[1] = new Knight(false);
        blackPieces[2] = new Bishop(false);
        blackPieces[3] = new Queen(false);
        blackPieces[4] = new King(false);
        blackPieces[5] = new Bishop(false);
        blackPieces[6] = new Knight(false);
        blackPieces[7] = new Rook(false);
        blackPieces[8] = new Pawn(false);
        blackPieces[9] = new Pawn(false);
        blackPieces[10] = new Pawn(false);
        blackPieces[11] = new Pawn(false);
        blackPieces[12] = new Pawn(false);
        blackPieces[13] = new Pawn(false);
        blackPieces[14] = new Pawn(false);
        blackPieces[15] = new Pawn(false);
    }

    @Override
    public ChessPiece getPieceAt(int r, int c){
        // 8x8 being the conventional size of a Chess board.
        if(r < 0 || r >= 8 || c < 0 || c >= 8){
            return null;
        }
        int pieceRep = board[r][c];
        if(pieceRep < 0){
            return null;
        }

        if((pieceRep % 2) == 0){
            return whitePieces[getWhiteIndex(pieceRep)];
        } else{
            return blackPieces[getBlackIndex(pieceRep)];
        }
    }

    @Override
    public Set<Point> getPiecePositions(){
        HashSet<Point> piecePositions = new HashSet<Point>();

        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(getPieceAt(r, c) != null){
                    piecePositions.add(new Point(r, c));
                }
            }
        }
        return piecePositions;
    }

    public void addPiece(ChessPiece cp, int r, int c){
        if(cp == null){
            return;
        }

        if(cp.isWhite()){
            int posIndex = WHITE_LIST.indexOf(cp);
            board[r][c] = getWhiteRep(posIndex);
        } else{
            int posIndex = BLACK_LIST.indexOf(cp);
            board[r][c] = getBlackRep(posIndex);
        }
    }

    @Override
    public void move(int r1, int c1, int r2, int c2){
        lastSrc = new Point(r1, c1);
        lastDest = new Point(r2, c2);
        board[r2][c2] = board[r1][c1];
        board[r1][c1] = -1;

        // Observable-related methods
        Point[] moveMade = {new Point(r1, c1), new Point(r2, c2)};
        setChanged();
        notifyObservers(moveMade);
    }

    @Override
    public void removePiece(int r, int c){
        board[r][c] = -1;
    }

    @Override
    public String toString(){
        StringBuilder boardBuilder = new StringBuilder();

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                ChessPiece cp = getPieceAt(row, col);
                if(cp != null){
                    char c = cp.getPieceName().charAt(0);
                    boardBuilder.append(c);
                    boardBuilder.append(" ");
                } else{
                    boardBuilder.append("# ");
                }
            }
            // Delete the last space appended
            boardBuilder.deleteCharAt(boardBuilder.length() - 1);
            boardBuilder.append("\n");
        }

        return boardBuilder.toString();
    }
}
