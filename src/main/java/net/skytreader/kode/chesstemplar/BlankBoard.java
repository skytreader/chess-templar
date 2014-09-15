package net.skytreader.kode.chesstemplar;

import java.util.Arrays;
import java.util.List;

import net.skytreader.kode.chesstemplar.pieces.ChessPiece;

/**
Grid implementation of Board that <i>does not</i> place anything on Board upon
initialization. Consequently, this class provides an additional method, addPiece
to add a ChessPiece in the blank board.

The purpose of this class is for quick scenario set-ups in tests. Use only for
tests; we might optimize GridBoard, but we sure as hell won't optimize BlankBoard.

@author Chad Estioco
*/
public class BlankBoard extends GridBoard{
    
    private final List<ChessPiece> WHITE_LIST;
    private final List<ChessPiece> BLACK_LIST;

    public BlankBoard(){
        // Call since it is OO-best practice to do so.
        super();
        // But reset everything!
        int[] rows = {0, 1, 6, 7};
        for(int row : rows){
            for(int col = 0; col < 8; col++){
                removePiece(row, col);
            }
        }

        WHITE_LIST = Arrays.asList(whitePieces);
        BLACK_LIST = Arrays.asList(blackPieces);
    }

    public void addPiece(ChessPiece cp, int r, int c){
        if(cp.isWhite()){
            int posIndex = WHITE_LIST.indexOf(cp);
            board[r][c] = getWhiteRep(posIndex);
        } else{
            int posIndex = BLACK_LIST.indexOf(cp);
            board[r][c] = getBlackRep(posIndex);
        }
    }
}
