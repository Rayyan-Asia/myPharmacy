package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.enums.LabTestType;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.LabTestRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;
import com.example.mypharmacy.ui.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import lombok.NonNull;


public class CreateLabTestActivity extends AppCompatActivity {
    Button submit;
    private Spinner typeSpinner;
    private EditText fileNameEditText;
    private EditText labTestDate;
    private String fileName;
    private LocalDate TEST_DATE;
    PersonRepository personRepository = new PersonRepositoryImpl(this);
    LabTestRepository repository  = new LabTestRepositoryImpl(this);
    private static final int REQUEST_CODE_PICK_FILE = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lab_test);
        submit = findViewById(R.id.submit_lab_type_button);
        typeSpinner = findViewById(R.id.lab_test_type_spinner);
        fileNameEditText = findViewById(R.id.lab_test_name_edit_text);
        labTestDate = findViewById(R.id.test_date_field);
        ArrayAdapter<LabTestType> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LabTestType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {

        submit.setOnClickListener(e -> {
            fileName = fileNameEditText.getText().toString().trim();
            if (fileName.equals("") || fileName.length() <= 3 ){
                Toast.makeText(getApplicationContext(), "Please enter a proper name.",
                        Toast.LENGTH_LONG).show();
            }else if (TEST_DATE == null || TEST_DATE.isAfter(LocalDate.now())){
                Toast.makeText(getApplicationContext(), "Please enter a proper test date",
                        Toast.LENGTH_LONG).show();
            } else{
                LabTestType type = (LabTestType) typeSpinner.getSelectedItem();
                if (type == LabTestType.ADD_FROM_LOCAL_STORAGE) {
                    // open storage
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_PICK_FILE);
                } else if (type == LabTestType.ADD_NEW) {
                    checkCameraPermission();
                }
            }

        });
        labTestDate.setOnClickListener(e -> {
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

                            labTestDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            TEST_DATE = LocalDate.of(year, monthOfYear+1, dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, start camera activity
                startCameraActivity();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
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
            Bitmap image  = (Bitmap) data.getExtras().get("data");
            File file = storeImage(image);
            saveImageToDatabase(file.getAbsolutePath());
            SwitchToListActivity();
        }
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == RESULT_OK && data.getData() != null) {
            Bitmap image;
            try {
                image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                File file = storeImage(image);
                saveImageToDatabase(file.getAbsolutePath());
                SwitchToListActivity();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SwitchToListActivity() {
        Intent intent = new Intent(this, LabTestListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
    private void saveImageToDatabase( String path) {
        // Get the DAO for the image table.
        LabTest test = new LabTest();

        Thread thread = new Thread() {
            @Override
            public void run() {
                test.testName = fileName;
                test.path = path;
                test.dateOfTest = TEST_DATE;
                test.personId= personRepository.getPerson().id;
                repository.insertLabTest(test);
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