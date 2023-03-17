package com.example.mypharmacy;

import android.app.Application;
import com.facebook.stetho.Stetho;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

