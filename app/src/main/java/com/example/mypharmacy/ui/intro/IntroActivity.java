package com.example.mypharmacy.ui.intro;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mypharmacy.MyApplication;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;

import javax.inject.Inject;
import java.time.LocalDate;

public class IntroActivity extends AppCompatActivity {
    @Inject
    PersonRepository personRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ((MyApplication) getApplicationContext()).appComponent.injectIntroActivity(this);


    }
    private void initListener() {
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();

                EditText firstNameField = findViewById(R.id.first_name_field);
                String firstName = firstNameField.getText().toString().trim();
                if (!firstName.isEmpty()) {
                    person.setFirstName(firstName);
                }

                EditText lastNameField = findViewById(R.id.last_name_field);
                String lastName = lastNameField.getText().toString().trim();
                if (!lastName.isEmpty()) {
                    person.setLastName(lastName);
                }

                Spinner genderSpinner = findViewById(R.id.gender_spinner);
                String gender = genderSpinner.getSelectedItem().toString();
                if (!gender.equals(getString(R.string.gender_hint))) {
                    person.setGender(gender);
                }

                DatePicker birthDayPicker = findViewById(R.id.birth_day);
                int year = birthDayPicker.getYear();
                int month = birthDayPicker.getMonth() + 1;
                int day = birthDayPicker.getDayOfMonth();
                LocalDate birthDate = LocalDate.of(year, month, day);
                person.setBirthDate(birthDate);

                EditText addressField = findViewById(R.id.address_field);
                String address = addressField.getText().toString().trim();
                if (!address.isEmpty()) {
                    person.setAddress(address);
                }

                EditText phoneNumberField = findViewById(R.id.phone_number_field);
                String phoneNumber = phoneNumberField.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    person.setPhoneNumber(Integer.parseInt(phoneNumber));
                }

                EditText weightField = findViewById(R.id.weight_field);
                String weight = weightField.getText().toString().trim();
                if (!weight.isEmpty()) {
                    person.setWeight(Float.parseFloat(weight));
                }

                EditText heightField = findViewById(R.id.height_field);
                String height = heightField.getText().toString().trim();
                if (!height.isEmpty()) {
                    person.setHeight(Float.parseFloat(height));
                }

                Spinner maritalStatusSpinner = findViewById(R.id.marital_status_spinner);
                String maritalStatus = maritalStatusSpinner.getSelectedItem().toString();
                if (!maritalStatus.equals(getString(R.string.marital_status_hint))) {
                    person.setMaritalStatus(maritalStatus);
                }

                Spinner bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
                String bloodType = bloodTypeSpinner.getSelectedItem().toString();
                if (!bloodType.equals(getString(R.string.blood_type_hint))) {
                    person.setBloodType(bloodType);
                }

                personRepository.insertPerson(person);

                Toast.makeText(getApplicationContext(), "Person saved successfully", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
