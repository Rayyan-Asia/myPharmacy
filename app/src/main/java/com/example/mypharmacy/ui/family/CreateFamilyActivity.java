package com.example.mypharmacy.ui.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.enums.DoctorSpecialty;
import com.example.mypharmacy.data.local.enums.StorageType;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;
import com.example.mypharmacy.ui.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateFamilyActivity extends AppCompatActivity {

    private EditText familyNameEditText;
    private Button addFamilyButton;

    private FamilyRepository familyRepository;
    private String name;

    private static final int REQUEST_CODE_PICK_FILE = 3;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION_CAMERA = 2;
    private Spinner storageLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        familyNameEditText = findViewById(R.id.edit_text_family_name);
        addFamilyButton = findViewById(R.id.create_family_button);
        storageLocation = findViewById(R.id.storage_location_spinner);
        ArrayAdapter<StorageType> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, StorageType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storageLocation.setAdapter(adapter);
        familyRepository = new FamilyRepositoryImpl(this);
        setupListeners();
    }

    private void setupListeners() {
        addFamilyButton.setOnClickListener(e->{
            name = familyNameEditText.getText().toString().trim();
            if(name.length() <= 3 || name.length() >= 32){
                familyNameEditText.setError("Please enter a proper name longer than 3 or less than 32 characters");
            }else{
               StorageType type =  (StorageType)storageLocation.getSelectedItem();
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
        Intent intent = new Intent(this,MainActivity.class);
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
        Family family = new Family();

        Thread thread = new Thread() {
            @Override
            public void run() {
                family.name = name;
                family.profilePicPath = path;
                familyRepository.insertFamily(family);
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