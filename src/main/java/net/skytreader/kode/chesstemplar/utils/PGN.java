package net.skytreader.kode.chesstemplar.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGN{
    private HashMap<String, String> metadata;
    private String gamefeed;

    protected static final Pattern METADATA_PATTERN = Pattern.compile("\\[.+\\]");
    protected static final Pattern METADATA_CAPTURE_PATTERN = Pattern.compile("\\s*\\[(.+)\\s+[\"\'](.+)[\"\']\\]\\s*");

    protected static final Pattern MOVE_STREAM_CAPTURE_PATTERN = Pattern.compile("\\d+\\.\\s+(" + NotationParser.CHECK_REGEX + ")");

    public PGN(String filename) throws IOException{
        metadata = new HashMap<String, String>();
        StringBuilder gamefeedBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        try{
            String line = br.readLine().trim();
            Matcher m = PGN.METADATA_CAPTURE_PATTERN.matcher(line);

            while(line != null && m.matches()){
                String dataKey = m.group(1);
                String dataVal = m.group(2);
                metadata.put(dataKey, dataVal);

                line = br.readLine();
                if(line != null){
                    m = PGN.METADATA_CAPTURE_PATTERN.matcher(line);
                }
            }

            while(line != null){
                gamefeedBuilder.append(line.trim() + " ");
                line = br.readLine();
            }

            gamefeed = gamefeedBuilder.toString().trim();
        } finally{
            br.close();
        }
    }

    public String getMetadata(String k){
        return metadata.get(k);
    }

    public String getGamefeed(){
        return gamefeed;
    }
}
