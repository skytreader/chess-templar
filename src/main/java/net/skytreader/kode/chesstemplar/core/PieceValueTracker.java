package net.skytreader.kode.chesstemplar.core;

/**
Use this class to keep track of the values of each piece. The allows for changes
in the value of a piece as the game progresses.

@author Chad Estioco
*/
public class PieceValueTracker{
    /*
    These are default values. See http://en.wikipedia.org/wiki/Chess_piece_relative_value
    */
    private float pawnVal = 1F;
    private float knightVal = 3F;
    private float bishopVal = 3F;
    private float rookVal = 5F;
    private float queenVal = 9F;
    private float kingVal = Float.POSITIVE_INFINITY;

    public float getPawnVal(){
        return pawnVal;
    }

    public void setPawnVal(float v){
        pawnVal = v;
    }

    public float getKnightVal(){
        return knightVal;
    }

    public void setKnightVal(float v){
        knightVal = v;
    }

    public float getBishopVal(){
        return bishopVal;
    }

    public void setBishopVal(float v){
        bishopVal = v;
    }

    public float getRookVal(){
        return rookVal;
    }

    public void setRookVal(float v){
        rookVal = v;
    }

    public float getQueenVal(){
        return queenVal;
    }

    public void setQueenVal(float v){
        queenVal = v;
    }

    public float getKingVal(){
        return kingVal;
    }
}
