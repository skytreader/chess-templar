package net.skytreader.kode.chesstemplar;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Knight;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Queen;
import net.skytreader.kode.chesstemplar.pieces.Rook;

public class GridBoard implements Board{
    private ChessPiece[] whitePieces = new ChessPiece[16];
    private ChessPiece[] blackPieces = new ChessPiece[16];
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
    private int[][] board = new int[8][8];

    private final ChessPiece[] BLACK_ARRANGEMENT = {new Rook(false), new Knight(false),
      new Bishop(false), new Queen(false), new King(false), new Bishop(false),
      new Knight(false), new Rook(false)};
      
    private final ChessPiece[] WHITE_ARRANGEMENT = {new Rook(true), new Knight(true),
      new Bishop(true), new Queen(true), new King(true), new Bishop(true),
      new Knight(true), new Rook(true)};

    public GridBoard(){
        generateWhitePieces();
        
        for(int i = 0; i < 8; i++){
        }

        generateBlackPieces();
    }
    
    /**
    Given the index of a piece in whitePieces, return its representation in
    the board.
    */
    private int getWhiteRep(int ai){
        return 2 * ai;
    }

    /**
    Given the index of a piece in blackPieces, return its representation
    in the board.
    */
    private int getBlackRep(int ai){
        return (2 * ai) + 1;
    }

    /**
    Given the representation of a whitePiece, return its index in whitePieces.
    */
    private int getWhiteIndex(int rep){
        return rep / 2;
    }

    /**
    Given the representation of a blackPiece, return its index in blackPieces.
    */
    private int getBlackIndex(int rep){
        return (rep - 1) / 2;
    }

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
        return null;
    }
}
