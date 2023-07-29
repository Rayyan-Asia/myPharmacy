package com.example.mypharmacy.ui.medRecord.appointment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.repositories.AppointmentDrugRepository;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentDrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;

import java.util.List;

public class AppointmentDrugListActivity extends AppCompatActivity {
    AppointmentDrugListAdapter appointmentDrugListAdapter;
    RecyclerView appointmentDrugRecycleView;
    private AppointmentDrugRepository appointmentDrugRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_drug_list);

        appointmentDrugRepository = new AppointmentDrugRepositoryImpl(this);
        appointmentDrugRecycleView = findViewById(R.id.drugs_list_app);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AppointmentDrug> appointmentDrugList = appointmentDrugRepository.getAllAppointmentDrugs(getIntent().getIntExtra("appointmentId",1));
                appointmentDrugListAdapter = new AppointmentDrugListAdapter(appointmentDrugList);
                appointmentDrugRecycleView.setAdapter(appointmentDrugListAdapter);
            }
        }).start();
        appointmentDrugRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}
