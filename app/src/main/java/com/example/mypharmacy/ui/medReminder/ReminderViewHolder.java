package com.example.mypharmacy.ui.medReminder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Prescription;
import org.jetbrains.annotations.NotNull;

public class ReminderViewHolder extends RecyclerView.ViewHolder {
    private TextView reminder_name, reminder_frequency;

    public ReminderViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        reminder_name = itemView.findViewById(R.id.reminder_name);
        reminder_frequency = itemView.findViewById(R.id.reminder_frequency);
    }

    public void bind(Prescription prescription) {
        reminder_name.setText(prescription.getName());
        reminder_frequency.setText(prescription.getFrequency());
    }

}
