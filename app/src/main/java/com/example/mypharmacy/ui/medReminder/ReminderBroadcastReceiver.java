package com.example.mypharmacy.ui.medReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.mypharmacy.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "reminder_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the reminder details from the intent
        // TODO: Get the reminder details we want from the intent
        int reminderId = intent.getIntExtra("reminderId", -1);
        String reminderName = intent.getStringExtra("reminderName");

        // Create and display the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_medication_reminder)
                .setContentTitle("Reminder: " + reminderName)
                .setContentText("Don't forget!") // TODO change this to something more useful
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(reminderId, builder.build());
    }
}

