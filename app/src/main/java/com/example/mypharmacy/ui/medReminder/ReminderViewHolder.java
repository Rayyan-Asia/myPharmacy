package com.example.mypharmacy.ui.medReminder;

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
            // Cancel the associated notification using the reminder_id as the notification_id
            int notificationId = reminder.getId();
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.itemView.getContext());
            notificationManager.cancel(notificationId);
        });
    }

}
