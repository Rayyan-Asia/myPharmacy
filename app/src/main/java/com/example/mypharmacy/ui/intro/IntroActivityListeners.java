package com.example.mypharmacy.ui.intro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.widget.*;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.ui.home.HomeFragment;
import com.example.mypharmacy.ui.menu.MenuActivity;

import java.time.LocalDate;
import java.util.Calendar;

public class IntroActivityListeners {
    private final IntroActivity activity;
    private final EditText birthDayField;
    public static LocalDate BIRTH_DAY;

    public IntroActivityListeners(IntroActivity activity) {
        this.activity = activity;
        this.birthDayField = activity.findViewById(R.id.birth_day_field);
        initListener();
    }

    private void initListener() {
        Button saveButton = activity.findViewById(R.id.save_button);
        birthDayField.setOnClickListener(e -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            birthDayField.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            BIRTH_DAY = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        saveButton.setOnClickListener(v -> {
            Person person = new Person();
            int check = 0;

            String firstName = activity.firstNameField.getText().toString().trim();
            if (!firstName.isEmpty()) {
                person.setFirstName(firstName);
            } else {
                activity.firstNameField.setError("First Name is Required");
                check = 1;
            }

            String lastName = activity.lastNameField.getText().toString().trim();
            if (!lastName.isEmpty()) {
                person.setLastName(lastName);
            } else {
                activity.lastNameField.setError("Last Name is Required");
                check = 1;
            }

            String gender = activity.genderSpinner.getSelectedItem().toString();
            if (!gender.equals(activity.getString(R.string.gender_hint))) {
                person.setGender(gender);
            } else {
                TextView genderError = (TextView) activity.genderSpinner.getSelectedView();
                genderError.setError("Gender is Required");
                genderError.setTextColor(Color.RED);
                genderError.setText("Gender is Required");
                check = 1;
            }

            String address = activity.addressField.getText().toString().trim();
            if (!address.isEmpty()) {
                person.setAddress(address);
            } else {
                activity.addressField.setError("Invalid Address");
                check = 1;
            }

            String phoneNumber = activity.phoneNumberField.getText().toString().trim();

            // Validate phone number length and leading zero
            if (phoneNumber.length() == 10 && phoneNumber.startsWith("0")) {
                // Check if the phone number contains only digits
                if (phoneNumber.matches("\\d+")) {
                    try {
                        person.setPhoneNumber(Integer.parseInt(phoneNumber));
                    } catch (NumberFormatException e) {
                        activity.phoneNumberField.setError("Invalid Phone Number");
                        check = 1;
                    }
                } else {
                    activity.phoneNumberField.setError("Invalid Phone Number (Digits only)");
                    check = 1;
                }
            } else {
                activity.phoneNumberField.setError("Invalid Phone Number (Should start with 0 and have 10 digits)");
                check = 1;
            }


            String weight = activity.weightField.getText().toString().trim();
            if (!weight.isEmpty()) {
                try {
                    person.setWeight(Float.parseFloat(weight));
                } catch (NumberFormatException e) {
                    activity.weightField.setError("Invalid Phone Number");
                    check = 1;
                }
            } else {
                activity.weightField.setError("Weight is Required");
                check = 1;
            }

            String height = activity.heightField.getText().toString().trim();
            if (!height.isEmpty()) {
                try {
                    person.setHeight(Float.parseFloat(height));
                } catch (NumberFormatException e) {
                    activity.heightField.setError("Invalid Height");
                    check = 1;
                }
            } else {
                activity.heightField.setError("Height is Required");
                check = 1;
            }

            String maritalStatus = activity.maritalStatusSpinner.getSelectedItem().toString();
            if (!maritalStatus.equals(activity.getString(R.string.marital_status_hint))) {
                person.setMaritalStatus(maritalStatus);
            }

            String bloodType = activity.bloodTypeSpinner.getSelectedItem().toString();
            if (!bloodType.equals(activity.getString(R.string.blood_type_hint))) {
                person.setBloodType(bloodType);
            }
            String birthDay = activity.birthDayField.toString();
            if (!birthDay.equals(activity.getString(R.string.select_a_date))) {
                person.setBirthDate(BIRTH_DAY);
            } else {
                birthDayField.setError("Birth Date is Required");
                check = 1;
            }
            if (check == 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        activity.personRepository.insertPerson(person);
                    }
                }).start();
                activity.startActivity(new Intent(activity.getApplicationContext(), MenuActivity.class));
                activity.finish();
            }
        });
    }
}
