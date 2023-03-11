package com.example.mypharmacy;

import android.app.Application;
import dagger.Component;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication extends Application {

    // Reference to the application graph that is used across the whole app
    public ApplicationComponent appComponent = DaggerApplicationComponent.create();
}

