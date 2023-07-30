package com.example.mypharmacy.ui.medRecord.appointment;

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
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AppointmentCardActivity extends AppCompatActivity {
    AppointmentAdapter appointmentAdapter;
    RecyclerView appointmentRecycleView;
    private AppointmentRepository appointmentRepository;
    private FloatingActionButton addAppointmentButton;
    private ActivityResultLauncher<Intent> createAppointmentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        appointmentRepository = new AppointmentRepositoryImpl(this);
        appointmentRecycleView = findViewById(R.id.appointments_list);

        createAppointmentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // Refresh the list by updating the adapter and notifying it that the data has changed
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Appointment> appointments = appointmentRepository.getAppointmentsByPersonId(1);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            appointmentAdapter.updateData(appointments);
                                            appointmentAdapter.notifyDataSetChanged();
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
                List<Appointment> appointments = appointmentRepository.getAppointmentsByPersonId(1);
                appointmentAdapter = new AppointmentAdapter(appointments);
                appointmentRecycleView.setAdapter(appointmentAdapter);
            }
        }).start();
        appointmentRecycleView.setLayoutManager(new LinearLayoutManager(this));
        addAppointmentButton = findViewById(R.id.add_appointment_button);
        addAppointmentButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, CreateAppointmentActivity.class);
            createAppointmentLauncher.launch(intent);
        });
    }

}
