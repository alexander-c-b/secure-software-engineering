package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.PBEConfig;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
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
        return getEncryptor(password).encrypt(data);
    }

    public static String decryptString(String encrypted, String password) {
        return getEncryptor(password).decrypt(encrypted);
    }

    private static StandardPBEStringEncryptor getEncryptor(String password) {
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        stringEncryptor.setAlgorithm("PBEWithMD5AndDES");
        stringEncryptor.setPassword(password);
        return stringEncryptor;
    }
}
