package net.skytreader.kode.chesstemplar;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

public class Board{
    private ChessPiece[] whitePieces = new ChessPiece[16];
    private ChessPiece[] blackPieces = new ChessPiece[16];
    
    /**
    When the board is constructed, all the pieces must be in the initial state
    of the game.
    */
    public Board(){
    }

    public ChessPiece getPieceAt(int r, int c){
        return null;
    }
}
