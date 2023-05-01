package com.example.mypharmacy.ui.medRecord.doctor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

import java.util.List;

public class DoctorCardActivity extends AppCompatActivity {
    DoctorAdapter doctorAdapter;
    RecyclerView doctorRecycleView;
    private DoctorRepository doctorRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        doctorRepository = new DoctorRepositoryImpl(this);
        doctorRecycleView = findViewById(R.id.doctors_list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Doctor> doctors = doctorRepository.getAllDoctors();
                doctorAdapter = new DoctorAdapter(doctors);
                doctorRecycleView.setAdapter(doctorAdapter);
            }
        }).start();
        doctorRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }
}
