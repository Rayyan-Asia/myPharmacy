package com.example.mypharmacy.ui.medRecord.doctor;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;

public class CreateDoctorActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText specialtyEditText;
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
        specialtyEditText = findViewById(R.id.specialtyEditText);
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
//                if (specialtyEditText.getText().toString().isEmpty()) {
//                    specialtyEditText.setError("Specialty is required");
//                    isValid = false;
//                }
//                if (phoneEditText.getText().toString().isEmpty()) {
//                    phoneEditText.setError("Phone is required");
//                    isValid = false;
//                } else if (phoneEditText.getText().toString().length() < 10) {
//                    phoneEditText.setError("Phone must be at least 10 digits");
//                    isValid = false;
//                }
//                if (emailEditText.getText().toString().isEmpty()) {
//                    emailEditText.setError("Email is required");
//                    isValid = false;
//                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
//                    emailEditText.setError("Invalid email format");
//                    isValid = false;
//                }
//                if (clinicalAddressEditText.getText().toString().isEmpty()) {
//                    clinicalAddressEditText.setError("Clinical address is required");
//                    isValid = false;
//                }

                // If input is valid, create new Doctor entity and insert into Room database
                try {
                    int phone = Integer.parseInt(phoneEditText.getText().toString());
                } catch (NumberFormatException e) {
                    isValidPhone = false;
                }
                if (isValid) {
                        Doctor doctor = new Doctor();
                        doctor.setName(nameEditText.getText().toString());
                        doctor.setSpecialty(specialtyEditText.getText().toString());
                        if(phoneEditText.getText().toString().isEmpty()){
                            doctor.setPhone(0);
                        } else if (isValidPhone){
                            doctor.setPhone(Integer.parseInt(phoneEditText.getText().toString()));
                        } else {
                            doctor.setPhone(0);
                        }
                        doctor.setEmail(emailEditText.getText().toString());
                        doctor.setClinicalAddress(clinicalAddressEditText.getText().toString());

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

