package net.skytreader.kode.chesstemplar;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

public interface Board{
    public ChessPiece getPieceAt(int r, int c);
}
