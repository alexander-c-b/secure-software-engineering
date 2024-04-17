package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class EncryptorUnitTest {
    private Context context;

    @Before
    public void setup() {
        context = Util.getTargetContext();
    }

    @Test
    public void encryptString_returnsChangedNonEmptyString() {
        final String data = "my test data";
        final String key = "my test key";
        final String encrypted = Encryptor.encryptString(data, key);
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        assertFalse(encrypted.contains(data));
    }

    @Test
    public void decryptString_decryptsEncrypted() {
        final String data = "my test data";
        final String key = "my test key";
        final String encrypted = Encryptor.encryptString(data, key);
        final String decrypted = Encryptor.decryptString(encrypted, key);
        assertNotNull(decrypted);
        assertEquals(data, decrypted);
    }

    @Test
    public void encrypt_encryptsFile() throws IOException {
        Encryptor encryptor = new Encryptor(context);
        String filePath = "test.txt";
        String password = "my test password";
        String data = "my test data";
        encryptor.encrypt(data, password, filePath);
        String encrypted = (new GameFile(filePath, context)).readFile();
        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        assertFalse(encrypted.contains(data));
        assertEquals(data, Encryptor.decryptString(encrypted, password));
    }

    @Test
    public void decrypt_decryptsFile() throws IOException {
        Encryptor encryptor = new Encryptor(context);
        String filePath = "test_file.txt";
        String password = "my test password";
        String data = "my test data";
        (new GameFile(filePath, context)).saveFile(
          Encryptor.encryptString(data, password));
        String decrypted = encryptor.decrypt(filePath, password);
        assertNotNull(decrypted);
        assertEquals(data, decrypted);
    }
}
