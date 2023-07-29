package com.example.mypharmacy.ui.medRecord.appointment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.ui.medRecord.appointment.AppointmentViewHolder;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {


    private List<Appointment> appointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_appointment_card, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        holder.bind(appointment);
        holder.itemView.setOnClickListener(e -> {
            Intent intent = new Intent(holder.itemView.getContext(), AppointmentDrugListActivity.class);
            intent.putExtra("appointmentId",appointment.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }
    public void updateData(List<Appointment> data) {
        appointmentList = data;
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

}
