package net.skytreader.kode.chesstemplar;

import net.skytreader.kode.chesstemplar.pieces.Bishop;
import net.skytreader.kode.chesstemplar.pieces.ChessPiece;
import net.skytreader.kode.chesstemplar.pieces.King;
import net.skytreader.kode.chesstemplar.pieces.Knight;
import net.skytreader.kode.chesstemplar.pieces.Pawn;
import net.skytreader.kode.chesstemplar.pieces.Queen;
import net.skytreader.kode.chesstemplar.pieces.Rook;

public class Board{
    private ChessPiece[] whitePieces = new ChessPiece[16];
    private ChessPiece[] blackPieces = new ChessPiece[16];

    /**
    When the board is constructed, all the pieces must be in the initial state
    of the game.
    */
    public Board(){
    }

    /**
    Get the piece at the specified row and column. Indexing is zero-based.
    Index 0,0 is the initial position of the black queen-side rook ("a8"). The
    ranks are rows and the files are columns.

    E.g.,
    b8 -> 0, 1
    */
    public ChessPiece getPieceAt(int r, int c){
        return null;
    }
}
