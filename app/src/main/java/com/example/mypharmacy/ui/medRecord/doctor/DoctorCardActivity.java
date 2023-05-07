package com.example.mypharmacy.ui.medRecord.doctor;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DoctorCardActivity extends AppCompatActivity {
    DoctorAdapter doctorAdapter;
    RecyclerView doctorRecycleView;
    private DoctorRepository doctorRepository;
    private FloatingActionButton addDoctorButton;
    private ActivityResultLauncher<Intent> createDoctorLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        doctorRepository = new DoctorRepositoryImpl(this);
        doctorRecycleView = findViewById(R.id.doctors_list);

        createDoctorLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // Refresh the list by updating the adapter and notifying it that the data has changed
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Doctor> doctors = doctorRepository.getAllDoctors();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            doctorAdapter.updateData(doctors);
                                            doctorAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                });

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Doctor> doctors = doctorRepository.getAllDoctors();
                doctorAdapter = new DoctorAdapter(doctors);
                doctorRecycleView.setAdapter(doctorAdapter);
            }
        }).start();
        doctorRecycleView.setLayoutManager(new LinearLayoutManager(this));
        addDoctorButton = findViewById(R.id.add_doctor_button);
        addDoctorButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, CreateDoctorActivity.class);
            createDoctorLauncher.launch(intent);
        });
    }

}
