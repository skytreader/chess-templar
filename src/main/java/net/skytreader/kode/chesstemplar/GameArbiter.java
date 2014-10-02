package net.skytreader.kode.chesstemplar;

/**
The GameArbiter imposes the rules of Chess. An arbiter is tied to a particular
game. You can't make an Arbiter judge on multiple boards at a time!

@author Chad Estioco
*/
public class GameArbiter{
    
    private Board board;
    private boolean whiteKingMoved;
    private boolean whiteKingsideRookMoved;
    private boolean whiteQueensideRookMoved;
    private boolean blackKingMoved;
    private boolean blackKingsideRookMoved;
    private boolean blackQueensideRookMoved;

    public GameArbiter(Board b){
        board = b;
        whiteKingMoved = false;
        whiteKingsideRookMoved = false;
        whiteQueensideRookMoved = false;
        blackKingMoved = false;
        blackKingsideRookMoved = false;
        blackQueensideRookMoved = false;
    }

    public boolean canWhiteKingCastle(){
        return false;
    }

    public boolean canBlackKingCastle(){
        return false;
    }

    public boolean isWhiteKingChecked(){
        return false;
    }

    public boolean isBlackKingChecked(){
        return false;
    }

    public boolean isEndgame(){
        return false;
    }
}
