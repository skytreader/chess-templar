package net.skytreader.kode.chesstemplar.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PGN{
    private HashMap<String, String> metadata;
    private String gamefeed;

    protected static final Pattern METADATA_PATTERN = Pattern.compile("\\[.+\\]");
    protected static final Pattern METADATA_CAPTURE_PATTERN = Pattern.compile("\\[(.+)\\s+[\"\'](.+)[\"\']\\]");

    public PGN(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        try{
            String line = br.readLine().trim();

            while(line != null && PGN.METADATA_PATTERN.matcher(line).matches()){
                line = br.readLine().trim();
            }
        } finally{
            br.close();
        }
    }
}
