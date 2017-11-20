package com.bftv.tell.a;

import android.app.Application;

/**
 * Author by Less on 17/10/20.
 */

public class App extends Application{

    public static Application sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

    }
}
