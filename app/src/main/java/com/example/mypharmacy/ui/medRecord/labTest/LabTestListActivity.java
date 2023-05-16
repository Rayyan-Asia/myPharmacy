package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;
import com.example.mypharmacy.data.local.repositories.impl.LabTestRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.appointment.AppointmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LabTestListActivity extends AppCompatActivity {

    LabTestAdapter labTestAdapter;
    private FloatingActionButton addTestButton;
    private RecyclerView testsListView;
    private LabTestRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_list);
        initViews();
        repository = new LabTestRepositoryImpl(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<LabTest> labTests = repository.listLabTests();
                labTestAdapter = new LabTestAdapter(labTests);
                testsListView.setAdapter(labTestAdapter);
            }
        }).start();

    }

    private void initViews() {
        addTestButton = findViewById(R.id.add_lab_test_button);
        testsListView = findViewById(R.id.lab_test_recyclerview);
    }
}