package com.example.mypharmacy.ui.medReminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.Reminder;
import com.example.mypharmacy.data.local.repositories.ReminderRepository;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.ReminderRepositoryImplementation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    private TextView reminder_name, reminder_dosage;
    private Button deleteReminderButton;

    public ReminderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        reminder_name = itemView.findViewById(R.id.reminder_name);
        reminder_dosage = itemView.findViewById(R.id.reminder_dosage);
        deleteReminderButton = itemView.findViewById(R.id.delete_reminder_button);

    }

    public void bind(Reminder reminder) {
        reminder_name.setText(reminder.getName());
        //Drug drug = new DrugRepositoryImpl(this.itemView.getContext()).getDrug(reminder.getDrugId());
        //reminder_dosage.setText(reminder.getDosage() + " " + drug.getDosageUnit()); --> relies on unit implementation
        reminder_dosage.setText(reminder.getDosage());
        deleteReminderButton.setOnClickListener(e -> {
            // Delete the reminder from the repository
            new Thread(() -> {
                ReminderRepository reminderRepository = new ReminderRepositoryImplementation(this.itemView.getContext());
                reminderRepository.deleteReminder(reminder.getId());
                List<Reminder> reminderList = reminderRepository.getActiveReminders();

                // update the recycler view reminderRecyclerView from MedicationReminderFragment
                MedicationReminderFragment.reminderRecyclerView.post(() -> {
                    MedicationReminderFragment.reminderRecyclerView.getAdapter().notifyDataSetChanged();
                    MedicationReminderFragment.reminderAdapter.updateData(reminderList);
                });
            }).start();
            // Cancel the scheduled alarm for the reminder
            AlarmManager alarmManager = (AlarmManager) this.itemView.getContext().getSystemService(this.itemView.getContext().ALARM_SERVICE);
            Intent intent = new Intent(this.itemView.getContext(), ReminderBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.itemView.getContext(), reminder.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        });
    }

}
