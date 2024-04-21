package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import android.util.Base64;
import okio.BufferedSource;
import okio.Okio;

public class Authenticator extends AppCompatActivity {

    private EditText enterUser;
    private EditText enterPass;
    private Button createButton;
    private JsonEncoder jsonEncoder;
    private Map<String, String> userDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        enterUser = findViewById(R.id.EnterUser);
        enterPass = findViewById(R.id.EnterPass);
        createButton = findViewById(R.id.createB);
        jsonEncoder = new JsonEncoder(this);

        loadUserDatabase();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        String username = enterUser.getText().toString().trim();
        String password = enterPass.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Please enter a username and password.");
            return;
        }

        if (!username.matches("[A-Za-z][A-Za-z0-9_-]{0,11}")) {
            showToast("Username does not meet the requirements.");
            return;
        }

        if (!isStrongPassword(password)) {
            showToast("Password does not meet the strength requirements.");
            return;
        }

        if (userExists(username)) {
            showToast("User already exists.");
            return;
        }

        String hashedPassword = hashPassword(password);
        String hashedSaltedPassword = hashPassword(password + "KEY");
        userDatabase.put(username, hashedPassword);
        saveUserDatabase();
        showToast("Account created successfully.");

        try {
            jsonEncoder.initializePastScores(
              username, hashedPassword, hashedSaltedPassword);
        } catch (IOException e) {
            showToast("Failed to initialize past scores.");
        }

        startActivity(new Intent(Authenticator.this, MainActivity.class));
    }

    private boolean userExists(String username) {
        return userDatabase.containsKey(username);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 12 &&
                // checks if password has at least one number
                password.matches(".*[0-9].*") &&
                // checks if password has at least one uppercase letter
                password.matches(".*[A-Z].*") &&
                // checks if password has at least one lowercase letter
                password.matches(".*[a-z].*") &&
                // checks if password has at least one special character
                password.matches(".*[^A-Za-z0-9].*");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.encodeToString(hashedPassword, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            showToast("Failed to hash password");
            return null;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void loadUserDatabase() {
        try (FileInputStream fis = openFileInput("account.json")) {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(fis));
            // Reads JSON data from file
            String jsonData = bufferedSource.readUtf8();
            bufferedSource.close();

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Map.class, String.class, String.class);
            JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
            // converting map to JSON
            userDatabase = jsonAdapter.fromJson(jsonData);
            if (userDatabase == null) userDatabase = new HashMap<>();
        } catch (FileNotFoundException e) {
            userDatabase = new HashMap<>();
        } catch (IOException e) {
            showToast("Error in loading data.");
        }
    }

    private void saveUserDatabase() {
        try (FileOutputStream fos = openFileOutput("account.json", Context.MODE_PRIVATE)) {
            // moshi to handle JSON operations
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Map.class, String.class, String.class);
            JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
            // converting map to JSON
            String jsonData = jsonAdapter.toJson(userDatabase);

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            // write JSON to the file
            writer.write(jsonData);
            writer.close();
        } catch (IOException e) {
            showToast("Error in saving data.");
        }
    }
}