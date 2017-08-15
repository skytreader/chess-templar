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

    private final String[] METADATA_KEYS = {
        "Event", "Site", "Date", "Round", "White", "Black", "Result", "ECO",
        "TimeControl", "Time", "WhiteClock", "BlackClock", "PlyCount"
    };
    private final String[] METADATA_VALS = {
        "Local Event", "Local Site", "2017.08.01", "1", "chad", "PyChess.py",
        "0-1", "B23", "300+0", "21:49:00", "0:00:43.901", "0:04:54.370", "50"
    };

    private final String[] GAMESTREAM = {
        "1. e4 c5 2. Nc3 Nc6 3. Nd5 e5 4. c3 Nge7 5. Nf3 Nxd5 6. exd5 Ne7 7. Nxe5 Nxd5",
		"8. d4 cxd4 9. cxd4 Bb4+ 10. Bd2 O-O 11. Qh5 Bxd2+ 12. Kd1 Nf6 13. Qh4 g5 14.",
		"Qh6 Ne4 15. Bd3 d5 16. f3 g4 17. Qh5 gxf3 18. gxf3 Be3 19. Nxf7 Nf2+ 20. Ke2",
		"Rxf7 21. Rhg1+ Ng4 22. fxg4 Bxg1 23. Rxg1 Qb6 24. g5 Qxb2+ 25. Ke3 Qf2# 0-1",
    };

    private String[] constructMetadata(){
        assert METADATA_KEYS.length == METADATA_VALS.length;
        int limit = METADATA_KEYS.length;
        String[] metadata = new String[limit]; 
        for(int i = 0; i < limit; i++){
            metadata[i] = "[" + METADATA_KEYS[i] + " \"" + METADATA_VALS + "\"]";
        }
        return metadata;
    }
    
    @Test
    public void testMetadataPattern(){
        String[] metadata = constructMetadata();
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
    public void testParsingTypical() throws IOException{
        File pgn = tempFolder.newFile("test.pgn");
        PrintWriter pgnWriter = new PrintWriter(new BufferedWriter(new FileWriter("test.pgn")));
        try{
            String[] metadata = constructMetadata();
            for(String md: metadata){
                pgnWriter.println(md);
            }
            pgnWriter.println("");
            for(String gs: GAMESTREAM){
                pgnWriter.println(gs);
            }
        } finally{
            pgnWriter.close();
        }

        PGN pgnObj = new PGN("test.pgn");
        int limit = METADATA_KEYS.length;
        for(int i = 0; i < limit; i++){
            Assert.assertEquals(pgnObj.getMetadata(METADATA_KEYS[i]), METADATA_VALS[i]);
        }

        Assert.assertNull(pgnObj.getMetadata("balderDASHxkcd"));
    }
}
