package edu.floridapoly.securesoftware.spring24.SocialEngineerGame;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

public class Util {
    public static Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
