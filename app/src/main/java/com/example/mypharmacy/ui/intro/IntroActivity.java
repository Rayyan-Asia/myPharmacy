package com.example.mypharmacy.ui.intro;

import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;

public class IntroActivity extends AppCompatActivity {
    PersonRepository personRepository;
    EditText firstNameField;
    EditText lastNameField;
    Spinner genderSpinner;
    EditText birthDayField;
    EditText addressField;
    EditText phoneNumberField;
    EditText weightField;
    EditText heightField;
    Spinner maritalStatusSpinner;
    Spinner bloodTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        personRepository = new PersonRepositoryImpl(this);
        firstNameField = findViewById(R.id.first_name_field);
        lastNameField = findViewById(R.id.last_name_field);
        genderSpinner = findViewById(R.id.gender_spinner);
        birthDayField = findViewById(R.id.birth_day_field);
        addressField = findViewById(R.id.address_field);
        phoneNumberField = findViewById(R.id.phone_number_field);
        weightField = findViewById(R.id.weight_field);
        heightField = findViewById(R.id.height_field);
        maritalStatusSpinner = findViewById(R.id.marital_status_spinner);
        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        IntroActivityListeners introActivityListeners =new IntroActivityListeners(this);
    }


}
