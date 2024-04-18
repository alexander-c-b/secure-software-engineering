package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameFile {
    private final Context context;
    private final String path;

    GameFile(String path) {
        this.path = path;
        this.context = App.getContext();
    }

    GameFile(String path, Context context) throws IOException {
        this.context = context;
        this.path = path;
    }

    public String readFile() throws IOException {
        FileInputStream inputStream = context.openFileInput(path);
        StringBuilder builder = new StringBuilder();
        int character;
        while ((character = inputStream.read()) != -1) {
            builder.append((char) character);
        }
        inputStream.close();
        return builder.toString();
    }

    public void saveFile(String contents) throws IOException {
        FileOutputStream outputStream =
                context.openFileOutput(path, Context.MODE_PRIVATE);
        outputStream.write(contents.getBytes());
        outputStream.close();
    }
}
