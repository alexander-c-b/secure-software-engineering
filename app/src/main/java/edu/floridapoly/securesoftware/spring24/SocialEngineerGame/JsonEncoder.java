package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;
import android.content.res.AssetManager;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

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

    /////////////// TRY TO LOGIN USE THIS BECAUSE IF THE USER AND PASS DOES NOT EXISTS THIS WOULD SHOW ////////////////////////
    public List<PastScore> loadPastScores(String username, String passwordHash)
      throws IOException {
        String key = passwordHash + "KEY";
        String filename = username + "." + passwordHash + ".json";
        String data = new Encryptor(context).decrypt(filename, key);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(data);
    }

    private void savePastScores(
      List<PastScore> pastScores, String username, String passwordHash
    ) throws IOException {
        String key = passwordHash + "KEY";
        String filename = username + "." + passwordHash + ".json";
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        String data = jsonAdapter.toJson(pastScores);
        new Encryptor(context).encrypt(filename, data, key);
    }

    public void savePastScore(PastScore pastScore, String username, String passwordHash)
      throws IOException {
        List<PastScore> pastScores = loadPastScores(username, passwordHash);
        pastScores.add(pastScore);
        savePastScores(pastScores, username, passwordHash);
    }

    public void initializePastScores(String username, String passwordHash)
      throws IOException {
        savePastScores(new ArrayList<>(), username, passwordHash);
    }
}
