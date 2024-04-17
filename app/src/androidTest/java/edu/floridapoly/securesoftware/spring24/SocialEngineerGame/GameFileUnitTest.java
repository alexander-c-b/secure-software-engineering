package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class GameFileUnitTest {
    private static Context context;

    @Before
    public void setup() {
        context = Util.getTargetContext();
    }

    @Test
    public void constructor_doesNotThrow() throws IOException {
        new GameFile("test.txt", context);
    }

    @Test
    public void readFile_returnsContentsSavedBySaveFile() throws IOException {
        final String filename = "test.txt";
        final String contents = "test string data";
        final GameFile firstFile = new GameFile(filename, context);
        firstFile.saveFile(contents);
        final GameFile secondFile = new GameFile(filename, context);
        final String newContents = secondFile.readFile();
        assertEquals(contents, newContents);
    }
}