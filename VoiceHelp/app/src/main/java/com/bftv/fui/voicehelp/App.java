package com.bftv.fui.voicehelp;

import android.app.Application;
import android.content.Context;

import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.SimpleLog;

/**
 * Author by Less on 17/10/11.
 */

public class App extends Application{

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SimpleLog.sIsDebug = true;
    }
}
