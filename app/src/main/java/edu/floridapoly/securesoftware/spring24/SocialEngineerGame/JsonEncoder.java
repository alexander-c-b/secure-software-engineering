package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Question.class);
        JsonAdapter<List<Question>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(new GameFile(QUESTIONS_FILE, context).readFile());
    }

    public List<PastScore> loadPastScores(String username, String passwordHash)
      throws IOException {
        String key = passwordHash + "KEY";
        String filename = getFilename(username, passwordHash);
        String data = new Encryptor(context).decrypt(filename, key);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        return jsonAdapter.fromJson(data);
    }

    private String getFilename(String username, String passwordHash) {
        return username + "." + passwordHash + ".json";
    }

    public void savePastScores(
      List<PastScore> pastScores, String username, String passwordHash
    ) throws IOException {
        String key = passwordHash + "KEY";
        String filename = getFilename(username, passwordHash);
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, PastScore.class);
        JsonAdapter<List<PastScore>> jsonAdapter = moshi.adapter(type);
        String data = jsonAdapter.toJson(pastScores);
        new Encryptor(context).encrypt(data, key, filename);
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
