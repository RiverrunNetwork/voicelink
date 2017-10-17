package com.bftv.fui.voicehelp;
import android.app.Application;
import android.content.Context;

/**
 * Author by Less on 17/10/11.
 */

public class App extends Application{

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
