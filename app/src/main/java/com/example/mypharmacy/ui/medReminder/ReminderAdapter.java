package com.example.mypharmacy.ui.medReminder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.data.local.entities.Prescription;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderViewHolder> {
    private List<Prescription> prescriptionList;

    public ReminderAdapter(List<Prescription> prescriptionList) {
        this.prescriptionList = prescriptionList;
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
        Prescription prescription = prescriptionList.get(position);
        holder.bind(prescription);
        holder.itemView.setOnClickListener(e -> {

        });
    }

    public void updateData(List<Prescription> data) {
        prescriptionList = data;
    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }
}
