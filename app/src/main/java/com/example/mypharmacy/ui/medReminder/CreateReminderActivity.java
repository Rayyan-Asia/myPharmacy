package com.example.mypharmacy.ui.medReminder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.Prescription;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.PrescriptionRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PrescriptionRepositoryImpl;

import java.time.LocalDate;
import java.util.Calendar;

public class CreateReminderActivity extends AppCompatActivity {
    private EditText nameEditText, daysEditText, dosageEditText, frequencyEditText, categoryEditText, typeEditText, startDateEditText, endDateEditText;
    private Button createButton;
    private LocalDate START_DATE, END_DATE;
    private DrugRepository drugRepository;
    private DoctorRepository doctorRepository;
    private PrescriptionRepository prescriptionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        // repositories
        drugRepository = new DrugRepositoryImpl(this);
        doctorRepository = new DoctorRepositoryImpl(this);
        prescriptionRepository = new PrescriptionRepositoryImpl(this);
        // form elements
        nameEditText = findViewById(R.id.edit_text_name);
        dosageEditText = findViewById(R.id.edit_text_dosage);
        frequencyEditText = findViewById(R.id.edit_text_frequency);
        daysEditText = findViewById(R.id.edit_text_days);
        categoryEditText = findViewById(R.id.edit_text_category);
        typeEditText = findViewById(R.id.edit_text_type);
        startDateEditText = findViewById(R.id.edit_text_startDate);
        endDateEditText = findViewById(R.id.edit_text_endDate);

        createButton = findViewById(R.id.button_create);

        // listeners
        startDateEditText.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        START_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        endDateEditText.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        endDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        END_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });


        createButton.setOnClickListener(view -> {
            if (isValidForm()) {
                String name = nameEditText.getText().toString();
                String dosage = dosageEditText.getText().toString();
                String frequency = frequencyEditText.getText().toString();
                // may use if a Drug object was needed
                String category = categoryEditText.getText().toString();
                String type = typeEditText.getText().toString();

                // Prescription instantiation
                Prescription prescription = new Prescription();
                prescription.setName(name);
                prescription.setDosage(dosage);
                prescription.setFrequency(frequency);
                prescription.setStartDate(START_DATE);
                prescription.setEndDate(END_DATE);
                Doctor doctor = new Doctor();
                new Thread(() -> {
                    doctorRepository.insertDoctor(doctor);
                    finish();
                }).start();
                Drug drug = new Drug();
                new Thread(() -> {
                    drugRepository.insertDrug(drug);
                    finish();
                }).start();
                prescription.setDrugId(drug.getId());
                prescription.setDoctorId(doctor.getId());
                new Thread(() -> {
                    doctorRepository.insertDoctor(doctor);
                    drugRepository.insertDrug(drug);
                    prescriptionRepository.insertPrescription(prescription);
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }).start();
            }
        });
    }

    private boolean isValidForm() {
        boolean isValid = true;

        if (nameEditText.getText().toString().isEmpty()) {
            nameEditText.setError("Required");
            isValid = false;
        }

        if (dosageEditText.getText().toString().isEmpty()) {
            dosageEditText.setError("Required");
            isValid = false;
        }

        if (frequencyEditText.getText().toString().isEmpty()) {
            frequencyEditText.setError("Required");
            isValid = false;
        }
        // doesn't matter for now
//        if (categoryEditText.getText().toString().isEmpty()) {
//            categoryEditText.setError("Required");
//            isValid = false;
//        }
//
//        if (typeEditText.getText().toString().isEmpty()) {
//            typeEditText.setError("Required");
//            isValid = false;
//        }

        if (START_DATE == null) {
            Toast.makeText(this, "Please select a starting date", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if (END_DATE == null) {
            Toast.makeText(this, "Please select an end date", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}

