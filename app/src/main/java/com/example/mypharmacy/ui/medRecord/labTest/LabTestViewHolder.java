package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;

import java.io.File;

public class LabTestViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView title;
    private TextView date;

    private ImageView imageView;

    public LabTestViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        title = itemView.findViewById(R.id.test_card_title);
        date = itemView.findViewById(R.id.test_card_date);
        imageView = itemView.findViewById(R.id.file_view_lab_test);
    }

    public void bind(LabTest labTest) {
        title.setText(labTest.testName);
        date.setText(labTest.dateOfTest.toString());
        File file = new File(labTest.path);
        if (file.exists()) {
            Glide.with(context)
                    .load(file) // Assuming the image path is stored in the 'path' variable of the LabTest object
                    .into(imageView);
        } else {
            Log.e("ERROR", "FILE NOT FOUND!!!! " + labTest.path);
        }

    }


}