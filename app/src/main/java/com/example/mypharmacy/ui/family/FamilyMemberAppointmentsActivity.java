package com.example.mypharmacy.ui.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.ui.medRecord.appointment.AppointmentAdapter;

import java.util.List;

public class FamilyMemberAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int personId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_appintments);
        recyclerView = findViewById(R.id.family_member_appointments_recycler_view);
        personId = (int) getIntent().getExtras().get("personId");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrieveData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    private void retrieveData() {
        RetrieveAppointmentsByPersonIdTask retrieveAppointmentsTask = new RetrieveAppointmentsByPersonIdTask(this, new RetrieveAppointmentsByPersonIdTask.OnAppointmentsRetrievedListener() {
            @Override
            public void onAppointmentsRetrieved(List<Appointment> appointments) {
                // Handle the retrieved appointments here
                if (appointments != null && !appointments.isEmpty()) {
                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(appointments);
                    recyclerView.setAdapter(appointmentAdapter);
                } else {
                    // No appointments found or some error occurred.
                }
            }
        });

        retrieveAppointmentsTask.execute(personId);
    }

}