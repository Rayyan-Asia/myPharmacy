package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;
import com.example.mypharmacy.data.local.repositories.impl.LabTestRepositoryImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;

public class LabTestListActivity extends AppCompatActivity {

    LabTestAdapter labTestAdapter;
    private List<LabTest> labTests;
    private FloatingActionButton addTestButton;
    private RecyclerView testsListView;
    private LabTestRepository repository;
    Context context;

    private ActivityResultLauncher<Intent> labTestActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_list);
        initViews();
        repository = new LabTestRepositoryImpl(this);
        context = this;
        initListeners();
        loadData();

        labTestActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            loadData();
                        }
                    }
                });
    }

    private void initViews() {
        addTestButton = findViewById(R.id.add_lab_test_button);
        testsListView = findViewById(R.id.lab_test_recyclerview);
        testsListView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixels = 20;
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(spacingInPixels);
        testsListView.addItemDecoration(itemDecoration);
    }

    private void initListeners() {

        addTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSurvey();
            }
        });

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                labTests = repository.listLabTests();
                if (labTests.size() == 0)
                    switchToSurvey();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        labTestAdapter = new LabTestAdapter(labTests, context);
                        testsListView.setAdapter(labTestAdapter);
                    }
                });
            }
        }).start();
    }

    private void switchToSurvey() {
        Intent intent = new Intent(this, CreateLabTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        labTestActivityResultLauncher.launch(intent);
    }
}
