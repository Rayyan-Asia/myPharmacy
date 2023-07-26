package com.example.mypharmacy.ui.medRecord.drug;

import android.content.Context;
import android.content.Intent;
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
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.doctor.CreateDoctorActivity;
import com.example.mypharmacy.ui.medRecord.doctor.DoctorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DrugCardActivity extends AppCompatActivity {
    DrugAdapter drugAdapter;
    RecyclerView drugRecycleView;
    private DrugRepository drugRepository;
    private FloatingActionButton addDrugButton;

    private Context context;
    private ActivityResultLauncher<Intent> createDrugLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug);

        drugRepository = new DrugRepositoryImpl(this);
        drugRecycleView = findViewById(R.id.drugs_list);
        context = this.getApplicationContext();
        createDrugLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // Refresh the list by updating the adapter and notifying it that the data has changed
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    List<Drug> drugs = drugRepository.getAllDrugs();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            drugAdapter.updateData(drugs);
                                            drugAdapter.notifyDataSetChanged();
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
                List<Drug> drugs = drugRepository.getAllDrugs();
                drugAdapter = new DrugAdapter(drugs, DrugCardActivity.this);
                drugRecycleView.setAdapter(drugAdapter);
            }
        }).start();
        drugRecycleView.setLayoutManager(new LinearLayoutManager(this));
        addDrugButton = findViewById(R.id.add_drug_button);
        addDrugButton.setOnClickListener(e -> {
            Intent intent = new Intent(this, CreateDrugActivity.class);
            createDrugLauncher.launch(intent);
        });
    }
}
