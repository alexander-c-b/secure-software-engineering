package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import android.content.Context;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@RunWith(MockitoJUnitRunner.class)
public class GameFileUnitTest {
    private static Context context;

    private static TemporaryFolder folder;

    @BeforeClass
    public static void setup() throws IOException {
        folder = new TemporaryFolder();
        folder.create();
        context = Mockito.mock(Context.class);
        when(context.getFilesDir()).thenReturn(new File(folder.getRoot().getPath()));
    }

    @Test
    public void constructor_doesNotThrow() throws IOException {
        new GameFile("test.txt", context);
    }

    @Test
    public void readFile_returnsContents() throws IOException {
        final String filename = "test.txt";
        final String contents = "test string data";
        try (final PrintWriter writer = new PrintWriter(
          new File(folder.getRoot(), filename))) {
            writer.print(contents);
        }
        final GameFile file = new GameFile(filename, context);
        final String newContents = file.readFile();
        assertEquals(contents, newContents);
    }

    @Test
    public void saveFile_writesContents() throws IOException {
        final String filename = "test.txt";
        final String contents = "test string data";
        final GameFile file = new GameFile(filename, context);
        file.saveFile(contents);
        final String newContents = file.readFile();
        assertEquals(contents, newContents);
    }
}