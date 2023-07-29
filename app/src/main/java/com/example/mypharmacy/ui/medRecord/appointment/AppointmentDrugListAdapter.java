package com.example.mypharmacy.ui.medRecord.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.ui.medRecord.appointment.AppointmentViewHolder;

import java.util.List;

public class AppointmentDrugListAdapter extends RecyclerView.Adapter<AppointmentDrugListViewHolder> {


    private List<AppointmentDrug> appointmentDrugList;

    public AppointmentDrugListAdapter(List<AppointmentDrug> appointmentDrugList) {
        this.appointmentDrugList = appointmentDrugList;
    }

    @NonNull
    @Override
    public AppointmentDrugListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_drug_card, parent, false);
        return new AppointmentDrugListViewHolder(view, view.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentDrugListViewHolder holder, int position) {
        AppointmentDrug appointmentDrug = appointmentDrugList.get(position);
        holder.bind(appointmentDrug);
        holder.itemView.setOnClickListener(e -> {

        });
    }
    public void updateData(List<AppointmentDrug> data) {
        appointmentDrugList = data;
    }

    @Override
    public int getItemCount() {
        return appointmentDrugList.size();
    }

}
