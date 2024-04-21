package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.IOException;

public class Encryptor {
    private final Context context;

    public Encryptor(Context context) {
        this.context = context;
    }

    public Encryptor() {
        this.context = App.getContext();
    }

    public void encrypt(String data, String passwordHashSalted, String filePath)
      throws IOException {
        GameFile file = new GameFile(filePath, context);
        file.saveFile(encryptString(data, passwordHashSalted));
    }

    public String decrypt(String filePath, String passwordHashSalted)
      throws IOException {
        GameFile file = new GameFile(filePath, context);
        return decryptString(file.readFile(), passwordHashSalted);
    }

    public static String encryptString(String data, String passwordHashSalted) {
        return getEncryptor(passwordHashSalted).encrypt(data);
    }

    public static String decryptString(String encrypted, String passwordHashSalted) {
        return getEncryptor(passwordHashSalted).decrypt(encrypted);
    }

    private static StandardPBEStringEncryptor getEncryptor(String passwordHashSalted) {
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        stringEncryptor.setPassword(passwordHashSalted);
        return stringEncryptor;
    }
}
