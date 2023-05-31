package com.example.mypharmacy.ui.medReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.app.NotificationManagerCompat;

public class ReminderNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve the notification ID and message from the intent extras
        int notificationId = intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("message");

        // Handle the notification action
        if (intent.getAction() != null && intent.getAction().equals("ACTION_DELETE_REMINDER")) {
            // Handle the delete action

            // TODO: Handle the delete action for the reminder with the specified ID

            // Cancel the notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.cancel(notificationId);

            Toast.makeText(context, "Reminder deleted", Toast.LENGTH_SHORT).show();
        } else {
            // Handle the notification click action
            // You can customize the behavior when the user clicks the notification
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
