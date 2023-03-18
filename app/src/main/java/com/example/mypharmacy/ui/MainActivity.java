package com.example.mypharmacy.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.intro.IntroActivity;
import com.example.mypharmacy.ui.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PersonRepository personRepository = new PersonRepositoryImpl(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Person person = personRepository.getPerson();
                // Pass the person object back to the main thread using a Handler
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startNextActivity(person);
                    }
                });
            }
        }).start();
    }

    private void startNextActivity(Person person) {
        if (person == null) {
            Intent introIntent = new Intent(this, IntroActivity.class);
            startActivityWithDelay(introIntent, 5000);
        } else {
            Intent homeIntent = new Intent(this, MenuActivity.class);
            startActivity(homeIntent);
            System.out.println(person);
        }
    }

    private void startActivityWithDelay(final Intent intent, long delayMillis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(intent);
            }
        }, delayMillis);
    }

}