package com.example.mypharmacy;

import android.app.Application;
import com.facebook.stetho.Stetho;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

