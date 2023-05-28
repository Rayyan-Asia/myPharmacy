package com.example.mypharmacy.ui.medReminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.ReminderRepository;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.ReminderRepositoryImplementation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CreateReminderActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDosage;
    private RadioGroup radioGroup;
    private TimePicker timePicker;
    private Button buttonCreate;
    private Spinner drugSpinner;

    private ReminderRepository reminderRepository = new ReminderRepositoryImplementation(this);

    private static final String CHANNEL_ID = "reminder_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDosage = findViewById(R.id.edit_text_dosage);
        radioGroup = findViewById(R.id.radio_group);
        timePicker = findViewById(R.id.time_picker);
        drugSpinner = findViewById(R.id.spinner_drug);
        buttonCreate = findViewById(R.id.button_create);

        buttonCreate.setOnClickListener(v -> createReminder());
        // Populate spinner with drugs
        populateDrugSpinner();
    }

    private void populateDrugSpinner() {
        // retrieve drugs from database
        List<Drug> drugs = new DrugRepositoryImpl(this).getAllDrugs();
        // create a list of drug names
        List<String> drugNames = new ArrayList<>();
        for (Drug drug : drugs) {
            drugNames.add(drug.getName());
        }
        // create an adapter and set it to the spinner
        ArrayAdapter<Drug> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drugs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drugSpinner.setAdapter(adapter);

    }

    private void createReminder() {
        String name = editTextName.getText().toString().trim();
        String dosage = editTextDosage.getText().toString().trim();

        if (name.isEmpty() || dosage.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String frequency = selectedRadioButton.getText().toString();

        int hour;
        int minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Timestamp timestamp = getTimestamp(hour, minute);

        List<Timestamp> timestamps = getTimestampsForFrequency(timestamp, frequency);

        Reminder reminder = new Reminder();
        reminder.setName(name);
        reminder.setDosage(dosage);
        reminder.setTimes(timestamps);
        // Get drugId from spinner
        Drug selectedDrug = (Drug) drugSpinner.getSelectedItem();
        reminder.setDrugId(selectedDrug.getId());
        // Add the reminder to the repository
        reminderRepository.insertReminder(reminder);

        // Activate a notification for the reminder
        activateNotification(reminder);

        Toast.makeText(this, "Reminder created successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private Timestamp getTimestamp(int hour, int minute) {
        long currentMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentMillis);
        timestamp.setHours(hour);
        timestamp.setMinutes(minute);
        timestamp.setSeconds(0);
        return timestamp;
    }

    private List<Timestamp> getTimestampsForFrequency(Timestamp timestamp, String frequency) {
        List<Timestamp> timestamps = new ArrayList<>();

        // Add the initial timestamp
        timestamps.add(timestamp);

        // Add additional timestamps based on the selected frequency
        switch (frequency) {
            case "Daily":
                // Add timestamps for the next year of days
                for (int i = 1; i < 365; i++) {
                    Timestamp nextDayTimestamp = new Timestamp(timestamp.getTime() + ((long) i * 24 * 60 * 60 * 1000));
                    timestamps.add(nextDayTimestamp);
                }
                break;
            case "Weekly":
                // Add timestamps for the next year of weeks
                for (int i = 1; i < 48; i++) {
                    Timestamp nextWeekTimestamp = new Timestamp(timestamp.getTime() + ((long) i * 7 * 24 * 60 * 60 * 1000));
                    timestamps.add(nextWeekTimestamp);
                }
                break;
            case "Monthly":
                // Add timestamps for the next year of months
                for (int i = 1; i < 12; i++) {
                    Timestamp nextMonthTimestamp = new Timestamp(timestamp.getTime());
                    nextMonthTimestamp.setMonth(nextMonthTimestamp.getMonth() + i);
                    timestamps.add(nextMonthTimestamp);
                }
                break;
        }

        return timestamps;
    }

    private void activateNotification(Reminder reminder) {
        createNotificationChannel();
        List<Timestamp> timestamps = reminder.getTimes();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        for (int i = 0; i < timestamps.size(); i++) {
            Timestamp timestamp = timestamps.get(i);
            int notificationId = reminder.getId();

            // Create an intent to launch the notification
            Intent intent = new Intent(this, ReminderNotificationReceiver.class);
            intent.putExtra("notificationId", notificationId);
            intent.putExtra("message", "It's time to take your medication!");

            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Set the alarm to trigger at the specified timestamp
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timestamp.getTime(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp.getTime(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp.getTime(), pendingIntent);
            }
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminder Channel";
            String description = "Channel for medication reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        } else Toast.makeText(this, "Notification channel could not be created", Toast.LENGTH_SHORT).show();
    }
}
