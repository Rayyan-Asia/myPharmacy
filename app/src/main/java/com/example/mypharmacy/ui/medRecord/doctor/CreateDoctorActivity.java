package com.example.mypharmacy.ui.medRecord.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.enums.DoctorSpecialty;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

public class CreateDoctorActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Spinner specialtySpinner;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText clinicalAddressEditText;
    private Button createDoctorButton;

    private DoctorRepository doctorRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doctor);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        specialtySpinner = findViewById(R.id.specialty_spinner);


        // Create an ArrayAdapter using the values from the enum and the default spinner layout
        ArrayAdapter<DoctorSpecialty> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DoctorSpecialty.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(adapter);

        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        clinicalAddressEditText = findViewById(R.id.clinicalAddressEditText);
        createDoctorButton = findViewById(R.id.createDoctorButton);

        // Initialize Room database
        doctorRepository = new DoctorRepositoryImpl(this);

        // Set click listener for createDoctorButton
        createDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields
                boolean isValid = true;
                boolean isValidPhone = true;
                if (nameEditText.getText().toString().isEmpty()) {
                    nameEditText.setError("Name is required");
                    isValid = false;
                }

                // If input is valid, create new Doctor entity and insert into Room database
                try {
                    int phone = Integer.parseInt(phoneEditText.getText().toString());
                } catch (NumberFormatException e) {
                    isValidPhone = false;
                }
                if (isValid) {
                        Doctor doctor = new Doctor();
                        doctor.setName(nameEditText.getText().toString().trim());
                        doctor.setSpecialty(specialtySpinner.getSelectedItem().toString());
                        if(phoneEditText.getText().toString().isEmpty()){
                            doctor.setPhone(0);
                        } else if (isValidPhone){
                            doctor.setPhone(Integer.parseInt(phoneEditText.getText().toString()));
                        } else {
                            doctor.setPhone(0);
                        }
                        doctor.setEmail(emailEditText.getText().toString().trim());
                        doctor.setClinicalAddress(clinicalAddressEditText.getText().toString().trim());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                doctorRepository.insertDoctor(doctor);
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("newDoctorAdded", true);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }
                        }).start();


                }
            }
        });

    }

}

