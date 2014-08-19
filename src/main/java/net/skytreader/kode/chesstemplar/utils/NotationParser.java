package net.skytreader.kode.chesstemplar.utils;

import java.awt.Point;

import java.util.Pattern;

/**
We would be parsing standard algebraic notation as required by FIDE.
*/
public class NotationParser{
    private final String LEGAL_COLUMNS = "abcdefgh";
    private final String NOTATION_REGEX = "[KQRBN]?[abcdefgh][1-8]";
    
    public static Point parse(String s){
    }

}
