package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okio.BufferedSource;
import okio.Okio;

public class JsonEncoder {
    private static final String QUESTIONS_FILE = "questions.json";

    private final Context context;

    public JsonEncoder() {
        context = App.getContext();
    }

    public JsonEncoder(Context context) {
        this.context = context;
    }

    public List<Question> loadQuestionDataString(String data) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Question.class);
        JsonAdapter<List<Question>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(data);
    }

    public List<Question> loadQuestionData() throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(QUESTIONS_FILE);
        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
        String data = bufferedSource.readUtf8();
        bufferedSource.close();

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Question.class);
        JsonAdapter<List<Question>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(data);
    }

    public List<PastScore> loadPastScores(
      String username, String passwordHash, String passwordHashSalted
    ) throws IOException {
        String filename = getFilename(username, passwordHash);
        File file = new File(context.getFilesDir(), filename);
        if (!file.exists()) {
            Log.e("JsonEncoder", "File does not exist: " + filename);
            return new ArrayList<>(); // Return an empty list or handle this case
            // appropriately
        } else {
            Log.d("FileAccess", "File exists and ready to be used: " + filename);
        }
        String data = new Encryptor(context).decrypt(filename, passwordHashSalted);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(data);
    }

    private String getFilename(String username, String passwordHash) {
        // Sanitize both username and passwordHash to ensure filename is valid
        String safeUsername = sanitizeFilename(username);
        String safePasswordHash = sanitizeFilename(passwordHash);
        return safeUsername + "." + safePasswordHash + ".encrypted";
    }

    // Make sure file has a valid name
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^A-Za-z0-9]", "_");
    }

    public void savePastScores(
      List<PastScore> pastScores, String username, String passwordHash,
      String passwordHashSalted
    ) throws IOException {
        String filename = getFilename(username, passwordHash);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        String data = jsonAdapter.toJson(pastScores);
        Log.d("JsonEncoder", "Saving data to: " + filename);
        Log.d("JsonEncoder", "Saved past score: " + pastScores);
        new Encryptor(context).encrypt(data, passwordHashSalted, filename);
    }


    public void savePastScore(
      PastScore pastScore, String username, String passwordHash,
      String passwordHashSalted
    ) throws IOException {
        List<PastScore> pastScores =
          loadPastScores(username, passwordHash, passwordHashSalted);
        pastScores.add(pastScore);
        savePastScores(pastScores, username, passwordHash, passwordHashSalted);
    }

    public void initializePastScores(
      String username, String passwordHash, String passwordHashSalted
    ) throws IOException {
        savePastScores(new ArrayList<>(), username, passwordHash, passwordHashSalted);
    }
}
