package net.skytreader.kode.chesstemplar.utils;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;

public class PGNTest{
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String[] metadata = {
        "[Event \"Local Event\"]",
		"[Site \"Local Site\"]",
		"[Date \"2017.08.01\"]",
		"[Round \"1\"]",
		"[White \"chad\"]",
		"[Black \"PyChess.py\"]",
		"[Result \"0-1\"]",
		"[ECO \"B23\"]",
		"[TimeControl \"300+0\"]",
		"[Time \"21:49:00\"]",
		"[WhiteClock \"0:00:43.901\"]",
		"[BlackClock \"0:04:54.370\"]",
		"[PlyCount \"50\"]"
    };
    
    @Test
    public void testMetadataPattern(){
        for(int i = 0; i < metadata.length; i++){
            Assert.assertTrue(PGN.METADATA_PATTERN.matcher(metadata[i]).matches());
        }
    }

    @Test
    public void testMetadataCapturePattern(){
        String test = "[Event \"Local Event\"]";
        Matcher m = PGN.METADATA_CAPTURE_PATTERN.matcher(test);
        Assert.assertTrue(m.matches());
        Assert.assertEquals("Event", m.group(1));
        Assert.assertEquals("Local Event", m.group(2));

        String trimTest = "   [Site \"Local Site\"]   \n";
        m = PGN.METADATA_CAPTURE_PATTERN.matcher(trimTest);
        Assert.assertTrue(m.matches());
        Assert.assertEquals("Site", m.group(1));
        Assert.assertEquals("Local Site", m.group(2));
    }

    @Test
    public void testParsing() throws IOException{
        File pgn = tempFolder.newFile("test.pgn");
        PrintWriter pgnWriter = new PrintWriter(new BufferedWriter(new FileWriter("test.pgn")));
        try{
            for(String md: metadata){
                pgnWriter.println(md);
            }
        } finally{
            pgnWriter.close();
        }

        PGN pgnObj = new PGN("test.pgn");
    }
}
