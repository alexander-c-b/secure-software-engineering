package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okio.BufferedSource;
import okio.Okio;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button logButton;
    private Button createButton;
    private Map<String, String> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText) findViewById(R.id.textUsername);
        passwordInput = (EditText) findViewById(R.id.textPassword);
        logButton = (Button) findViewById(R.id.loginButton);
        createButton = (Button) findViewById(R.id.cButton);

        loadUserDatabase();

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Authenticator.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Username or password cannot be empty.");
            return;
        }

        if (!userDatabase.containsKey(username)) {
            showToast("Username or password is incorrect.");
            return;
        }

        String storedHash = userDatabase.get(username);
        String enteredHash = hashPassword(password);

        if (storedHash == null || !storedHash.equals(enteredHash)) {
            showToast("Username or password is incorrect.");
            return;
        }
        Intent intent = new Intent(MainActivity.this, Game.class);
        startActivity(intent);
    }

    private String hashPassword(String password) {
        // This makes sure hashed passwords matches
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.encodeToString(hashedPassword, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            showToast("Failed to hash password");
            return null;
        }
    }

    private void loadUserDatabase() {
        try (FileInputStream fis = openFileInput("account.json")) {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(fis));
            String jsonData = bufferedSource.readUtf8();
            bufferedSource.close();

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Map.class, String.class, String.class);
            JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
            userDatabase = jsonAdapter.fromJson(jsonData);
            if (userDatabase == null) userDatabase = new HashMap<>();
        } catch (IOException e) {
            userDatabase = new HashMap<>();
            showToast("Error in loading data.");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
