package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import static org.junit.Assert.assertTrue;

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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

    private String getPasswordHashSalted() {
        return new String(new char[64]).replace('\0', 'b');
    }

    @Test
    public void savePastScores_savesEncryptedData() throws IOException {
        JsonEncoder jsonEncoder = new JsonEncoder(context);

        List<PastScore> pastScores =
          Arrays.asList(new PastScore(5, 3), new PastScore(5, 5));
        jsonEncoder.savePastScores(
          pastScores, "username", getPasswordHash(), getPasswordHashSalted());
        assertTrue(ArrayUtils.contains(
          context.fileList(),
          "username.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
            ".encrypted"
        ));
    }

    @Test
    public void savePastScore_savesSinglePastScore() throws IOException {
        JsonEncoder jsonEncoder = new JsonEncoder(context);
        jsonEncoder.savePastScores(Collections.singletonList(new PastScore(5, 3)),
          "username", getPasswordHash(), getPasswordHashSalted()
        );
        jsonEncoder.savePastScore(
          new PastScore(5, 4), "username", getPasswordHash(), getPasswordHashSalted());
        List<PastScore> loadedPastScores =
          jsonEncoder.loadPastScores("username", getPasswordHash(),
            getPasswordHashSalted()
          );
        assertTrue(loadedPastScores.get(0).totalQuestions == 5 &&
          loadedPastScores.get(0).correctAnswers == 3 &&
          loadedPastScores.get(1).totalQuestions == 5 &&
          loadedPastScores.get(1).correctAnswers == 4);
    }

}
