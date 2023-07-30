package com.example.mypharmacy.ui.intro;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.enums.StorageType;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.MainActivity;
import com.example.mypharmacy.ui.menu.MenuActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity {
    PersonRepository personRepository;
    public EditText firstNameField;
    EditText lastNameField;
    Spinner genderSpinner;
    EditText birthDayField;
    EditText addressField;
    EditText phoneNumberField;
    EditText weightField;
    EditText heightField;
    Spinner maritalStatusSpinner;
    Spinner bloodTypeSpinner;

    Spinner profilePicLocation;

    Button saveButton;

    private Person person = new Person();

    public static LocalDate BIRTH_DAY;
    private static final int REQUEST_CODE_PICK_FILE = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
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
        profilePicLocation = findViewById(R.id.user_profile_pic_spinner);
        saveButton = findViewById(R.id.save_button);
        ArrayAdapter<StorageType> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, StorageType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profilePicLocation.setAdapter(adapter);

        initListeners();
    }

    private void initListeners() {
        birthDayField.setOnClickListener(e -> {
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
                            birthDayField.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            BIRTH_DAY = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        saveButton.setOnClickListener(v -> {
            int check = 0;

            String firstName = firstNameField.getText().toString().trim();
            if (!firstName.isEmpty()) {
                person.setFirstName(firstName);
            } else {
                firstNameField.setError("First Name is Required");
                check = 1;
            }

            String lastName = lastNameField.getText().toString().trim();
            if (!lastName.isEmpty()) {
                person.setLastName(lastName);
            } else {
                lastNameField.setError("Last Name is Required");
                check = 1;
            }

            String gender = genderSpinner.getSelectedItem().toString();
            if (!gender.equals(getString(R.string.gender_hint))) {
                person.setGender(gender);
            } else {
                TextView genderError = (TextView) genderSpinner.getSelectedView();
                genderError.setError("Gender is Required");
                genderError.setTextColor(Color.RED);
                genderError.setText("Gender is Required");
                check = 1;
            }

            String address = addressField.getText().toString().trim();
            if (!address.isEmpty()) {
                person.setAddress(address);
            } else {
                addressField.setError("Invalid Address");
                check = 1;
            }

            String phoneNumber = phoneNumberField.getText().toString().trim();

            // Validate phone number length and leading zero
            if (phoneNumber.length() == 10 && phoneNumber.startsWith("0")) {
                // Check if the phone number contains only digits
                if (phoneNumber.matches("\\d+")) {
                    try {
                        person.setPhoneNumber(Integer.parseInt(phoneNumber));
                    } catch (NumberFormatException e) {
                        phoneNumberField.setError("Invalid Phone Number");
                        check = 1;
                    }
                } else {
                    phoneNumberField.setError("Invalid Phone Number (Digits only)");
                    check = 1;
                }
            } else {
                phoneNumberField.setError("Invalid Phone Number (Should start with 0 and have 10 digits)");
                check = 1;
            }


            String weight = weightField.getText().toString().trim();
            if (!weight.isEmpty()) {
                try {
                    person.setWeight(Float.parseFloat(weight));
                } catch (NumberFormatException e) {
                    weightField.setError("Invalid Phone Number");
                    check = 1;
                }
            } else {
                weightField.setError("Weight is Required");
                check = 1;
            }

            String height = heightField.getText().toString().trim();
            if (!height.isEmpty()) {
                try {
                    person.setHeight(Float.parseFloat(height));
                } catch (NumberFormatException e) {
                    heightField.setError("Invalid Height");
                    check = 1;
                }
            } else {
                heightField.setError("Height is Required");
                check = 1;
            }

            String maritalStatus = maritalStatusSpinner.getSelectedItem().toString();
            if (!maritalStatus.equals(getString(R.string.marital_status_hint))) {
                person.setMaritalStatus(maritalStatus);
            }

            String bloodType = bloodTypeSpinner.getSelectedItem().toString();
            if (!bloodType.equals(getString(R.string.blood_type_hint))) {
                person.setBloodType(bloodType);
            }
            String birthDay = birthDayField.toString();
            if (!birthDay.equals(getString(R.string.select_a_date))) {
                person.setBirthDate(BIRTH_DAY);
            } else {
                birthDayField.setError("Birth Date is Required");
                check = 1;
            }
            if (check == 0) {

                StorageType type =  (StorageType)profilePicLocation.getSelectedItem();
                if (type == StorageType.ADD_FROM_LOCAL_STORAGE) {
                    // open storage
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
                } else if (type == StorageType.ADD_NEW) {
                    checkCameraPermission();
                }
            }
        });
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSION_CAMERA);
        } else {
            // Permission is already granted, start camera activity
            startCameraActivity();
        }
    }

    public void startCameraActivity() {
        // Create an intent to start the Camera app.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the image from the data intent.
            Bitmap image = (Bitmap) data.getExtras().get("data");
            File file = storeImage(image);
            saveImageToDatabase(file.getAbsolutePath());
            setResult(RESULT_OK);
            finish();
        }
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK && data.getData() != null) {
            Bitmap image;
            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                File file = storeImage(image);
                saveImageToDatabase(file.getAbsolutePath());
                setResult(RESULT_OK);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show();
            }
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAndRemoveTask();
    }

    private File storeImage(Bitmap image) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName);
        // Try to write the image to the file.
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (IOException e) {
            // Handle the exception.
        }
        return file;
    }

    private void saveImageToDatabase(String path) {
        // Get the DAO for the image table.

        Thread thread = new Thread() {
            @Override
            public void run() {
                person.id = 1;
                person.profilePicPath = path;
                personRepository.insertPerson(person);
            }
        };
        thread.start();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish the current activity to remove it from the stack
    }




}
