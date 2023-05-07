package com.example.mypharmacy.ui.medRecord.appointment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView appointmentTitle;
    private TextView appointmentDoctor;
    private TextView appointmentSymptoms;
    private TextView appointmentDiagnosis;
    private TextView appointmentDate;

    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        appointmentTitle = itemView.findViewById(R.id.text_view_title);
        appointmentDoctor = itemView.findViewById(R.id.text_view_doctor_name);
        appointmentSymptoms = itemView.findViewById(R.id.text_view_symptoms);
        appointmentDiagnosis = itemView.findViewById(R.id.text_view_diagnosis);
        appointmentDate = itemView.findViewById(R.id.text_view_date_of_appointment);
    }

    public void bind(Appointment appointment) {
        appointmentTitle.setText(appointment.getTitle());
        appointmentSymptoms.setText(appointment.getSymptoms());
        appointmentDiagnosis.setText(appointment.getDiagnosis());
        appointmentDate.setText(appointment.getDateOfAppointment().toString());
        DoctorRepository doctorRepository = new DoctorRepositoryImpl(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                appointmentDoctor.setText(doctorRepository.getDoctor(appointment.getDoctorId()).getName());
            }
        }).start();
    }
}


