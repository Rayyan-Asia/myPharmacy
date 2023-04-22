package com.example.mypharmacy.ui.medRecord;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment{

    private List<Appointment> mAppointments;
    private RecyclerView mRecyclerView;
    private AppointmentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_records_appointments, container, false);
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl(view.getContext());
        // Initialize the list of Appointments and the RecyclerView adapter
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Appointment> appointmentList = appointmentRepository.getAllAppointments();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setAppointmentList(appointmentList);
                    }
                });
            }
        }).start();
        // Set up the RecyclerView with the AppointmentAdapter
        mRecyclerView = view.findViewById(R.id.appointments_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set up the Add button to add new Appointments to the list
        FloatingActionButton addButton = view.findViewById(R.id.add_appointment_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a dialog to add a new Appointment
                showAddAppointmentDialog();
            }
        });

        return view;
    }

    private void setAppointmentList(List<Appointment> appointmentList) {
        this.mAppointments = appointmentList;
        this.mAdapter = new AppointmentAdapter(mAppointments);
        this.mRecyclerView.setAdapter(mAdapter);
    }

    private void showAddAppointmentDialog() {
        // Implement the logic for showing a dialog to add a new Appointment
        // and adding the new Appointment to the mAppointments list
    }
}

