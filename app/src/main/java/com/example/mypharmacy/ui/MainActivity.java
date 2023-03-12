package com.example.mypharmacy.ui;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mypharmacy.R;
import com.example.mypharmacy.ui.intro.IntroActivity;
import com.example.mypharmacy.ui.name.NameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        Intent introIntent = new Intent(this, IntroActivity.class);
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(introIntent);
            }
        }, 5000);
    }
}