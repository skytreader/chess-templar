package net.skytreader.kode.chesstemplar.utils;

import java.awt.Point;

import java.util.regex.Pattern;

/**
We would be parsing standard algebraic notation as required by FIDE.

Our current reference is: http://en.wikipedia.org/wiki/Algebraic_notation_(chess)
but I would like something more official (like from FIDE).

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
    private static final String LEGAL_COLUMNS = "abcdefgh";
    private static final String LEGAL_PIECES = "KQRBN";
    private static final String NOTATION_REGEX = "[" + LEGAL_PIECES + "]?[" + LEGAL_COLUMNS + "][1-8]";
    private static final String CAPTURE_REGEX = "[" + LEGAL_PIECES + "]x[" + LEGAL_COLUMNS + "][1-8]";
    private static final String DISAMBIGUATION_REGEX = "[" + LEGAL_PIECES + "]([" + LEGAL_COLUMNS + "]|[1-8])" + LEGAL_COLUMNS + "[1-8]";

    private static final Pattern[] LEGAL_NOTATION = {
        Pattern.compile(NOTATION_REGEX),
        Pattern.compile(CAPTURE_REGEX),
        Pattern.compile(DISAMBIGUATION_REGEX),
    };
    
    /**
    Returns true if the given string is valid chess algebraic notation.
    */
    public static boolean isValid(String s){
        boolean isValid = false;
        for(Pattern p : LEGAL_NOTATION){
            if(p.matcher(s).matches()){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    
    public static Point parse(String s){
        return null;
    }

}
