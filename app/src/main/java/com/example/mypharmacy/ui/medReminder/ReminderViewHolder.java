package com.example.mypharmacy.ui.medReminder;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.repositories.ReminderRepository;
import com.example.mypharmacy.data.local.repositories.impl.ReminderRepositoryImplementation;
import com.example.mypharmacy.ui.medRecord.drug.DrugAliasAsyncTask;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView reminder_name, reminder_times;
    private ImageButton deleteReminderButton;

    private Context context;
    private List<Reminder> reminders;



    public ReminderViewHolder(@NonNull @NotNull View itemView, Context context, List<Reminder> reminderList) {
        super(itemView);
        reminders = reminderList;
        this.context = context;
        reminder_name = itemView.findViewById(R.id.reminder_name);
        reminder_times = itemView.findViewById(R.id.reminder_times);
        deleteReminderButton = itemView.findViewById(R.id.delete_reminder_button);
        itemView.setOnClickListener(this);
    }

    public void bind(Reminder reminder) {
        reminder_name.setText(reminder.getName());
        //Drug drug = new DrugRepositoryImpl(this.itemView.getContext()).getDrug(reminder.getDrugId());
        //reminder_dosage.setText(reminder.getDosage() + " " + drug.getDosageUnit()); --> relies on unit implementation

        // Retrieve the reminder times and format them as a string HH:mm
        List<Timestamp> reminderTimes = reminder.getTimes();
        StringBuilder timesStringBuilder = new StringBuilder();
        for (Timestamp timestamp : reminderTimes) {
            String formattedTime = convertTimestampToString(timestamp);
            if(!timesStringBuilder.toString().equals(""))
                timesStringBuilder.append(", ");
            timesStringBuilder.append(formattedTime);
        }
        reminder_times.setText(timesStringBuilder.toString().trim());

        deleteReminderButton.setOnClickListener(e -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this reminder?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // User confirmed deletion, proceed with the deletion process

                // Delete the reminder from the repository
                new Thread(() -> {
                    ReminderRepository reminderRepository = new ReminderRepositoryImplementation(itemView.getContext());
                    reminderRepository.deleteReminder(reminder.getId());

                    // Update the recycler view reminderRecyclerView from MedicationReminderFragment
                    List<Reminder> reminderList = reminderRepository.getActiveReminders();
                    MedicationReminderFragment.reminderRecyclerView.post(() -> {
                        MedicationReminderFragment.reminderRecyclerView.getAdapter().notifyDataSetChanged();
                        MedicationReminderFragment.reminderAdapter.updateData(reminderList);
                    });
                }).start();

                // Cancel the scheduled alarm for the reminder
                AlarmManager alarmManager = (AlarmManager) itemView.getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(itemView.getContext(), ReminderBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(itemView.getContext(), reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                alarmManager.cancel(pendingIntent);
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                // User cancelled deletion, do nothing
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    private String convertTimestampToString(Timestamp timestamp) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp.getTime());
        return timeFormat.format(date);
    }


    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            Reminder reminder = reminders.get(position);

            DrugConflictAsyncTask task = new DrugConflictAsyncTask(context, reminder);
            task.execute();
        }
    }
}
