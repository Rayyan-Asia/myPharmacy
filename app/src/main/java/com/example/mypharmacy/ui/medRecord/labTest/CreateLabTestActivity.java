package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.enums.LabTestType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lombok.var;


public class CreateLabTestActivity extends AppCompatActivity {

    Button submit;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lab_test);
        submit = findViewById(R.id.submit_lab_type_button);
        typeSpinner = findViewById(R.id.lab_test_type_spinner);
        ArrayAdapter<LabTestType> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LabTestType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        initListeners();
    }

    private void initListeners() {
//        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LabTestType type = (LabTestType) parent.getItemAtPosition(position);
//                // Do something with the selected type
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        submit.setOnClickListener(e -> {
            LabTestType type = (LabTestType) typeSpinner.getSelectedItem();
            if (type == LabTestType.ADD_FROM_LOCAL_STORAGE) {
                // open storage
            } else if (type == LabTestType.ADD_NEW) {
                // open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Start the activity for result.
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is RESULT_OK, then the user has taken a picture.
        if (resultCode == RESULT_OK) {
            // Get the image from the result.
            Bitmap image = (Bitmap) data.getExtras().get("data");

            // Save the image to a file.
            String filePath = saveImage(image);
        }


    }

    private String saveImage(Bitmap image) {
        // Create a file to save the image to.
        File file = new File(getExternalFilesDir(null), "image.jpg");

        try {
            // Save the image to the file.
            FileOutputStream fos = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the path to the file.
        return file.getPath();
    }

}