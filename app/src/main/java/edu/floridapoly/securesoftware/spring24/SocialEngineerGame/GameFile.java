package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameFile {
    private final File file;

    GameFile(String path, Context context) throws IOException {
        File baseDir = context.getFilesDir();
        file = new File(baseDir, path);
        file.createNewFile();
    }

    public String readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    public void saveFile(String contents) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(contents);
        writer.close();
    }
}
