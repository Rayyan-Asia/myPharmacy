package com.example.mypharmacy.ui.medRecord.doctor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

public class DoctorViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView doctor_name;
    private TextView doctor_specialty;
    private TextView doctor_phone;
    private TextView doctor_email;
    private TextView doctor_clinical_address;


    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        doctor_name = itemView.findViewById(R.id.doctor_name);
        doctor_specialty = itemView.findViewById(R.id.doctor_specialty);
        doctor_phone = itemView.findViewById(R.id.doctor_phone);
        doctor_email = itemView.findViewById(R.id.doctor_email);
        doctor_clinical_address = itemView.findViewById(R.id.doctor_clinical_address);

    }

    public void bind(Doctor doctor) {
        // Set the text for the TextViews based on the Appointment object
        new Thread(new Runnable() {
            @Override
            public void run() {
                doctor_name.setText(doctor.getName());
                doctor_specialty.setText(doctor.getSpecialty());
                doctor_phone.setText(""+doctor.getPhone());
                doctor_email.setText(doctor.getEmail());
                doctor_clinical_address.setText(doctor.getClinicalAddress());
            }
        }).start();
    }
}


