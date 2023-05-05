package com.example.mypharmacy.ui.medRecord.drug;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;

import java.time.LocalDate;
import java.util.Calendar;

public class CreateDrugActivity extends AppCompatActivity {
    private EditText nameEditText, descriptionEditText, manufacturerEditText, categoryEditText, typeEditText, expiryDateEditText;
    private Button createButton;
    private LocalDate EXPIRY_DATE;
    private DrugRepository drugRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_drug);
        drugRepository = new DrugRepositoryImpl(this);

        nameEditText = findViewById(R.id.edit_text_name);
        descriptionEditText = findViewById(R.id.edit_text_description);
        manufacturerEditText = findViewById(R.id.edit_text_manufacturer);
        categoryEditText = findViewById(R.id.edit_text_category);
        typeEditText = findViewById(R.id.edit_text_type);
        createButton = findViewById(R.id.button_create);
        expiryDateEditText = findViewById(R.id.expiryDateEditTexxt);
        expiryDateEditText.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            expiryDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            EXPIRY_DATE = LocalDate.of(year, monthOfYear, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        createButton.setOnClickListener(view -> {
            if (isValidForm()) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String manufacturer = manufacturerEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String type = typeEditText.getText().toString();


                Drug drug = new Drug();
                drug.setName(name);
                drug.setDescription(description);
                drug.setManufacturer(manufacturer);
                drug.setCategory(category);
                drug.setType(type);
                drug.setExpiryDate(EXPIRY_DATE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        drugRepository.insertDrug(drug);
                        Intent resultIntent = new Intent();
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
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

        if (descriptionEditText.getText().toString().isEmpty()) {
            descriptionEditText.setError("Required");
            isValid = false;
        }

        if (manufacturerEditText.getText().toString().isEmpty()) {
            manufacturerEditText.setError("Required");
            isValid = false;
        }

        if (categoryEditText.getText().toString().isEmpty()) {
            categoryEditText.setError("Required");
            isValid = false;
        }

        if (typeEditText.getText().toString().isEmpty()) {
            typeEditText.setError("Required");
            isValid = false;
        }

        if (EXPIRY_DATE == null) {
            Toast.makeText(this, "Please select an expiry date", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
}

