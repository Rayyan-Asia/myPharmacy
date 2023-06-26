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
import java.util.Calendar;
import java.util.List;

public class CreateReminderActivity extends AppCompatActivity {

    private EditText editTextDosage;
    private EditText editTextTime1, editTextTime2, editTextTime3;
    private RadioGroup frequencyRadioGroup;
    private RadioButton radioButtonOnce, radioButtonTwice, radioButtonThrice;
    private Button buttonCreate;
    private Spinner drugSpinner;
    public static String FREQUENCY = "";

    private final ReminderRepository reminderRepository = new ReminderRepositoryImplementation(this);

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
        // Create notification channel
        createNotificationChannel();
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
        if (validateTimeFields()) {
            // create a list of timestamps based on frequency
            List<Timestamp> timestamps;
            // mockup timestamps for testing
//        timestamps.add(new Timestamp(Calendar.getInstance().getTimeInMillis() + 1000)); // 1 second from now
//        timestamps.add(new Timestamp(Calendar.getInstance().getTimeInMillis() + 10000)); // 10 seconds from now
//        timestamps.add(new Timestamp(Calendar.getInstance().getTimeInMillis() + 20000)); // 20 seconds from now


            // create a reminder object
            timestamps = generateTimestamps();
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
        } else         // Toast an error message
            Toast.makeText(this, "The selected frequency requires all time fields to be filled", Toast.LENGTH_SHORT).show();

    }

    private List<Timestamp> generateTimestamps() {
        List<Timestamp> timestamps = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        if (FREQUENCY.equals("once")) {
            // Generate a single timestamp for a one-time reminder
            timestamps.add(getTimestampFromEditText(editTextTime1)); // Get the time from the EditText
        } else if (FREQUENCY.equals("twice")) {
            // Generate two timestamps for reminders to be triggered twice
            timestamps.add(getTimestampFromEditText(editTextTime1));
            timestamps.add(getTimestampFromEditText(editTextTime2));
        } else if (FREQUENCY.equals("thrice")) {
            // Generate three timestamps for reminders to be triggered thrice
            timestamps.add(getTimestampFromEditText(editTextTime1));
            timestamps.add(getTimestampFromEditText(editTextTime2));
            timestamps.add(getTimestampFromEditText(editTextTime3));
        }

        return timestamps;
    }

    private Timestamp getTimestampFromEditText(EditText editTextTime) {
        String time = editTextTime.getText().toString().trim();
        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        return getTimestamp(hour, minute);
    }

    private boolean validateTimeFields() {
        switch (FREQUENCY) {
            case "once":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                    return false;
                }
                return true;
            case "twice":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                    return false;
                }
                if (editTextTime2.getText().toString().isEmpty()) {
                    editTextTime2.setError("Time is required");
                    editTextTime2.requestFocus();
                    return false;
                }
                return true;
            case "thrice":
                if (editTextTime1.getText().toString().isEmpty()) {
                    editTextTime1.setError("Time is required");
                    editTextTime1.requestFocus();
                    return false;
                }
                if (editTextTime2.getText().toString().isEmpty()) {
                    editTextTime2.setError("Time is required");
                    editTextTime2.requestFocus();
                    return false;
                }
                if (editTextTime3.getText().toString().isEmpty()) {
                    editTextTime3.setError("Time is required");
                    editTextTime3.requestFocus();
                    return false;
                }
                return true;

        }
        return false;
    }

    private Timestamp getTimestamp(int hour, int minute) {
        long currentMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentMillis);
        timestamp.setHours(hour);
        timestamp.setMinutes(minute);
        timestamp.setSeconds(0);
        return timestamp;
    }

    /**
     *
     **/
    private void activateNotification(Reminder reminder) {
        List<Timestamp> timestamps = reminder.getTimes();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        for (Timestamp timestamp : timestamps) {
            // Get the current date and time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            // Set the reminder time
            Calendar reminderTime = Calendar.getInstance();
            reminderTime.setTime(timestamp);

            // Set the reminder time for today
            calendar.set(Calendar.HOUR_OF_DAY, reminderTime.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, reminderTime.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);

            // If the reminder time has already passed for today, schedule it for the next day
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) { // not sure about this
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            // Create an Intent for the BroadcastReceiver
            Intent intent = new Intent(this, ReminderBroadcastReceiver.class); // TODO: modify this to put useful data in the intent
            intent.putExtra("reminderId", reminder.getId());
            intent.putExtra("reminderName", reminder.getName());

            // Create a PendingIntent using a unique identifier
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Schedule the alarm (API(23) and above)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }


    }

    /**
     * Creates a notification channel for the reminder feature
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the notification channel
            CharSequence channelName = "Reminder Channel";
            String channelDescription = "Channel for medication reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        } else Toast.makeText(this, "Notification channel could not be created", Toast.LENGTH_SHORT).show();
    }
}
