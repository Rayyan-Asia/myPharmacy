package com.example.mypharmacy.ui.medReminder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.data.local.entities.Reminder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    private List<Reminder> reminderList;
    private Context context;

    public ReminderAdapter(List<Reminder> reminderList, Context context) {
        this.reminderList = reminderList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), com.example.mypharmacy.R.layout.activity_reminder_card, null);
        return new ReminderViewHolder(view,context,reminderList);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReminderViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        holder.bind(reminder);

        holder.itemView.setOnClickListener(holder);
    }

    public void updateData(List<Reminder> data) {
        reminderList = data;
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }
}
