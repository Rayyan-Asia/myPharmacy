package com.example.mypharmacy.ui.medReminder;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

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

    private EditText editTextDosage;
    private EditText editTextTime1, editTextTime2, editTextTime3;
    private RadioGroup frequencyRadioGroup;
    private RadioButton radioButtonOnce, radioButtonTwice, radioButtonThrice;
    private Button buttonCreate;
    private Spinner drugSpinner;
    public static String FREQUENCY = "";

    private ReminderRepository reminderRepository = new ReminderRepositoryImplementation(this);

    private static final String CHANNEL_ID = "reminder_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        editTextDosage = findViewById(R.id.edit_text_dosage);
        drugSpinner = findViewById(R.id.spinner_drug);
        buttonCreate = findViewById(R.id.button_create);
        editTextTime1 = findViewById(R.id.edit_text_time1);
        editTextTime2 = findViewById(R.id.edit_text_time2);
        editTextTime3 = findViewById(R.id.edit_text_time3);

        // get radio buttons
        frequencyRadioGroup = findViewById(R.id.radio_group_frequency);
        radioButtonOnce = findViewById(R.id.radio_button_once);
        radioButtonTwice = findViewById(R.id.radio_button_twice);
        radioButtonThrice = findViewById(R.id.radio_button_thrice);

        // default radio button selection
        frequencyRadioGroup.check(radioButtonOnce.getId()); // default selection
        FREQUENCY = "once";

        // set listeners
        frequencyRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updateEditTextVisibility(checkedId));
        buttonCreate.setOnClickListener(v -> createReminder());
        editTextTime1.setOnClickListener(v -> showTimePickerDialog(editTextTime1));
        editTextTime2.setOnClickListener(v -> showTimePickerDialog(editTextTime2));
        editTextTime3.setOnClickListener(v -> showTimePickerDialog(editTextTime3));
        // Populate spinner with drugs
        new Thread(this::populateDrugSpinner).start();
    }

    private void showTimePickerDialog(EditText editText) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String time = hourOfDay + ":" + minute;
            editText.setText(time);
        }, 0, 0, false);
        timePickerDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    private void updateEditTextVisibility(int checkedId) {
        switch (checkedId) {
            case R.id.radio_button_once:
                FREQUENCY = "once";
                editTextTime1.setVisibility(View.VISIBLE);
                editTextTime2.setVisibility(View.GONE);
                editTextTime3.setVisibility(View.GONE);
                break;
            case R.id.radio_button_twice:
                FREQUENCY = "twice";
                editTextTime1.setVisibility(View.VISIBLE);
                editTextTime2.setVisibility(View.VISIBLE);
                editTextTime3.setVisibility(View.GONE);
                break;
            case R.id.radio_button_thrice:
                FREQUENCY = "thrice";
                editTextTime1.setVisibility(View.VISIBLE);
                editTextTime2.setVisibility(View.VISIBLE);
                editTextTime3.setVisibility(View.VISIBLE);
                break;
        }
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
        String dosage = editTextDosage.getText().toString().trim(); // optional
        // validate time fields based on frequency

        validateTimeFields();
        // get time(String from edit text) based on frequency and create a timestamp

        Timestamp timestamp1 = getTimestampFromEditText(editTextTime1);
        Timestamp timestamp2 = getTimestampFromEditText(editTextTime2);
        Timestamp timestamp3 = getTimestampFromEditText(editTextTime3);

        // create a list of timestamps based on frequency
        List<Timestamp> timestamps = getTimestampsForFrequency(timestamp1, FREQUENCY);


        Reminder reminder = new Reminder();

        reminder.setTimes(timestamps);
        // Get drugId from spinner
        Drug selectedDrug = (Drug) drugSpinner.getSelectedItem();
        reminder.setName(selectedDrug.getName()); // name from drug
        if (!dosage.isEmpty()) {
            reminder.setDosage(dosage);
        }
        reminder.setDrugId(selectedDrug.getId());
        // Add the reminder to the repository
        new Thread(() -> {
            reminderRepository.insertReminder(reminder);
            // Activate a notification for the reminder
            activateNotification(reminder);
        }).start();

        runOnUiThread(() -> {
            Toast.makeText(this, "Reminder created successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private Timestamp getTimestampFromEditText(EditText editTextTime) {
        String time = editTextTime.getText().toString().trim();
        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        return getTimestamp(hour, minute);
    }

    private void validateTimeFields() {
        switch (FREQUENCY) {
            case "once":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                }
                break;
            case "twice":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                }
                if (editTextTime2.getText().toString().isEmpty()) {
                    editTextTime2.setError("Time is required");
                    editTextTime2.requestFocus();
                }
                break;
            case "thrice":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                }
                if (editTextTime2.getText().toString().isEmpty()) {
                    editTextTime2.setError("Time is required");
                    editTextTime2.requestFocus();
                }
                if (editTextTime3.getText().toString().isEmpty()) {
                    editTextTime3.setError("Time is required");
                    editTextTime3.requestFocus();
                }
                break;
        }
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

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
