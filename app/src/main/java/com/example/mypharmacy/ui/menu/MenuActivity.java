package com.example.mypharmacy.ui.menu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.mypharmacy.R;
import com.example.mypharmacy.ui.family.FamilyFragment;
import com.example.mypharmacy.ui.home.HomeFragment;
import com.example.mypharmacy.ui.medRecord.MedicalRecordFragment;
import com.example.mypharmacy.ui.medReminder.MedicationReminderFragment;
import com.example.mypharmacy.ui.menstrualCal.MenstrualCalendarFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView.setSelectedItemId(R.id.home_tab);
        setSupportActionBar(toolbar);

        // Set the listener for the BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.home_tab:
                    // Switch to the Home fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                    return true;
                case R.id.medical_record_tab:
                    // Switch to the Medical Record fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MedicalRecordFragment())
                            .commit();
                    return true;
                case R.id.medication_reminder_tab:
                    // Switch to the Medication Reminder fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MedicationReminderFragment())
                            .commit();
                    return true;
                case R.id.family_tab:
                    // Switch to the Family fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new FamilyFragment())
                            .commit();
                    return true;
                case R.id.menstrual_calendar_tab:
                    // Switch to the Menstrual Calendar fragment
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MenstrualCalendarFragment())
                            .commit();
                    return true;
                default:
                    return false;
            }
        });

        // Set the Home tab as the default tab
        bottomNavigationView.setSelectedItemId(R.id.home_tab);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.home_tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


