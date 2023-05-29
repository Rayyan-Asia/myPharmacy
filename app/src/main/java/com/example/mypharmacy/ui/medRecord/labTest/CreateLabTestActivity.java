package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.enums.LabTestType;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;
import com.example.mypharmacy.data.local.repositories.impl.LabTestRepositoryImpl;
import com.example.mypharmacy.ui.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import lombok.NonNull;


public class CreateLabTestActivity extends AppCompatActivity {

    Button submit;
    private Spinner typeSpinner;

    private EditText fileNameEditText;

    private String fileName;

    private String filePath;

    LabTestRepository repository  = new LabTestRepositoryImpl(this);
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lab_test);
        submit = findViewById(R.id.submit_lab_type_button);
        typeSpinner = findViewById(R.id.lab_test_type_spinner);
        fileNameEditText = findViewById(R.id.lab_test_name_edit_text);
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
            }else{
                LabTestType type = (LabTestType) typeSpinner.getSelectedItem();
                if (type == LabTestType.ADD_FROM_LOCAL_STORAGE) {
                    // open storage
                } else if (type == LabTestType.ADD_NEW) {
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
        }
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
        test.testName = fileName;
        test.path = path;
        Thread thread = new Thread() {
            @Override
            public void run() {
                repository.insertLabTest(test);
                Toast.makeText(CreateLabTestActivity.this, "Test saved successfully",
                        Toast.LENGTH_SHORT).show();
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