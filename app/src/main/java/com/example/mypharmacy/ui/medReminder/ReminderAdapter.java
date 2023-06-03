package com.example.mypharmacy.ui.medReminder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.data.local.entities.Reminder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    private List<Reminder> reminderList;

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @NonNull
    @NotNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), com.example.mypharmacy.R.layout.activity_reminder_card, null);
        return new ReminderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.bind(reminder);
        holder.itemView.setOnClickListener(e -> {
            // TODO: Open the reminder details activity
        });
    }

    public void updateData(List<Reminder> data) {
        reminderList = data;
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }
}
