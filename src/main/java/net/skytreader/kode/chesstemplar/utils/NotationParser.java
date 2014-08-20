package net.skytreader.kode.chesstemplar.utils;

import java.awt.Point;

import java.util.Pattern;

/**
We would be parsing standard algebraic notation as required by FIDE.

This uses the English notation for pieces that is,
    - K for king
    - Q for queen
    - R for rook
    - B for Bishop
    - N for knight

Valid and parseable strings describe _a single_ move by one player. Hence the
following is valid,

    Be5

for "Bishop to e5", but the following, a common notation for a chess turn, is not,

    e4 e5
*/
public class NotationParser{
    private final String LEGAL_COLUMNS = "abcdefgh";
    private final String NOTATION_REGEX = "[KQRBN]?[abcdefgh][1-8]";
    
    /**
    Returns true if the given string is valid chess algebraic notation.
    */
    public static boolean isLegal(String s){
    }
    
    public static Point parse(String s){
    }

}
