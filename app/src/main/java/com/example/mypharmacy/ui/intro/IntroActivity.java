package com.example.mypharmacy.ui.intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.home.HomeActivity;

import java.time.LocalDate;

public class IntroActivity extends AppCompatActivity {
    PersonRepository personRepository;
    EditText firstNameField;
    EditText lastNameField;
    Spinner genderSpinner;
    DatePicker birthDayPicker;
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
        birthDayPicker = findViewById(R.id.birth_day);
        addressField = findViewById(R.id.address_field);
        phoneNumberField = findViewById(R.id.phone_number_field);
        weightField = findViewById(R.id.weight_field);
        heightField = findViewById(R.id.height_field);
        maritalStatusSpinner = findViewById(R.id.marital_status_spinner);
        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        initListener();

    }
    private void initListener() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();

                String firstName = firstNameField.getText().toString().trim();
                if (!firstName.isEmpty()) {
                    person.setFirstName(firstName);
                } else {
                    firstNameField.setError("First Name is Required");
                }

                String lastName = lastNameField.getText().toString().trim();
                if (!lastName.isEmpty()) {
                    person.setLastName(lastName);
                } else {
                    lastNameField.setError("Last Name is Required");
                }

                String gender = genderSpinner.getSelectedItem().toString();
                if (!gender.equals(getString(R.string.gender_hint))) {
                    person.setGender(gender);
                }  else {
                    TextView genderError= (TextView) genderSpinner.getSelectedView();
                    genderError.setError("");
                    genderError.setTextColor(Color.RED);
                    genderError.setText("Pick a Gender");
                }

                int year = birthDayPicker.getYear();
                int month = birthDayPicker.getMonth() + 1;
                int day = birthDayPicker.getDayOfMonth();
                LocalDate birthDate = LocalDate.of(year, month, day);
                person.setBirthDate(birthDate);


                String address = addressField.getText().toString().trim();
                if (!address.isEmpty()) {
                    person.setAddress(address);
                }

                String phoneNumber = phoneNumberField.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    person.setPhoneNumber(Integer.parseInt(phoneNumber));
                } else {
                    phoneNumberField.setError("Last Name is Required");
                }


                String weight = weightField.getText().toString().trim();
                if (!weight.isEmpty()) {
                    person.setWeight(Float.parseFloat(weight));
                }

                String height = heightField.getText().toString().trim();
                if (!height.isEmpty()) {
                    person.setHeight(Float.parseFloat(height));
                }

                String maritalStatus = maritalStatusSpinner.getSelectedItem().toString();
                if (!maritalStatus.equals(getString(R.string.marital_status_hint))) {
                    person.setMaritalStatus(maritalStatus);
                }

                String bloodType = bloodTypeSpinner.getSelectedItem().toString();
                if (!bloodType.equals(getString(R.string.blood_type_hint))) {
                    person.setBloodType(bloodType);
                }

                personRepository.insertPerson(person);

                Toast.makeText(getApplicationContext(), "Person saved successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }

}
