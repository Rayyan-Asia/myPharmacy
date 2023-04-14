package com.example.mypharmacy.ui.medRecord;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

import java.util.List;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView appointmentTitleTextView;
    private TextView doctorNameTextView;
    private TextView dateOfAppointmentTextView;


    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        appointmentTitleTextView = itemView.findViewById(R.id.appointment_title);
        doctorNameTextView = itemView.findViewById(R.id.doctor_name_textview);
        dateOfAppointmentTextView = itemView.findViewById(R.id.date_of_appointment_textview);
    }

    public void bind(Appointment appointment) {
        DoctorRepository doctorRepository = new DoctorRepositoryImpl(context);
        // Set the text for the TextViews based on the Appointment object
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                appointmentTitleTextView.setText(appointment.title);
                doctorNameTextView.setText("Dr. " + doctorRepository.getDoctor(appointment.doctorId).getName());
                dateOfAppointmentTextView.setText(appointment.dateOfAppointment.toString());
            }
        }).start();
    }
}

