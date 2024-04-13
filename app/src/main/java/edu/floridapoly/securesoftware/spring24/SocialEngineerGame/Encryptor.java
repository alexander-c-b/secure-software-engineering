package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import org.jasypt.util.text.StrongTextEncryptor;

import java.io.IOException;

public class Encryptor {
    private final Context context;

    public Encryptor(Context context) {
        this.context = context;
    }

    public Encryptor() {
        this.context = App.getContext();
    }

    public void encrypt(String data, String password, String filePath)
      throws IOException {
        GameFile file = new GameFile(filePath, context);
        file.saveFile(encryptString(data, password));
    }

    public String decrypt(String filePath, String password) throws IOException {
        GameFile file = new GameFile(filePath, context);
        return decryptString(file.readFile(), password);
    }

    public static String encryptString(String data, String password) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(password);
        return textEncryptor.encrypt(data);
    }

    public static String decryptString(String encrypted, String password) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(password);
        return textEncryptor.decrypt(encrypted);
    }
}