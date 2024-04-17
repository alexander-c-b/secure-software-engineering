package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonEncoderUnitTest {
    private Context context;

    @Before
    public void setup() {
        context = Util.getTargetContext();
    }

    private String getPasswordHash() {
        return new String(new char[64]).replace('\0', 'a');
    }

    @Test
    public void savePastScores_savesEncryptedData() throws IOException {
        JsonEncoder jsonEncoder = new JsonEncoder(context);

        List<PastScore> pastScores =
          Arrays.asList(new PastScore(5, 3), new PastScore(5, 5));
        jsonEncoder.savePastScores(pastScores, "username", getPasswordHash());
        assertTrue(ArrayUtils.contains(
          context.fileList(),
          "username.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            ".json"
        ));
    }

    @Test
    public void savePastScore_savesSinglePastScore() throws IOException {
        JsonEncoder jsonEncoder = new JsonEncoder(context);
        jsonEncoder.savePastScores(
          Arrays.asList(new PastScore(5, 3)), "username", getPasswordHash());
        jsonEncoder.savePastScore(new PastScore(5, 4), "username", getPasswordHash());
        List<PastScore> loadedPastScores =
          jsonEncoder.loadPastScores("username", getPasswordHash());
        assertTrue(loadedPastScores.get(0).totalQuestions == 5 &&
          loadedPastScores.get(0).correctAnswers == 3 &&
          loadedPastScores.get(1).totalQuestions == 5 &&
          loadedPastScores.get(1).correctAnswers == 4);
    }

}
