package com.henry.apptools.app;

import android.app.Application;

import com.vice.curacfrlib.CurAcFr;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CurAcFr.init(this);
    }
}
