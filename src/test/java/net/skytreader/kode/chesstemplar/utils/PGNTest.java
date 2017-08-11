package net.skytreader.kode.chesstemplar.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

public class PGNTest{
    
    @Test
    public void testMetadataPattern(){
        String[] metadata = {
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
}
