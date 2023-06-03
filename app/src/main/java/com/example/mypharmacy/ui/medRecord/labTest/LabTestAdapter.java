package com.example.mypharmacy.ui.medRecord.labTest;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;

import java.io.File;
import java.util.List;

public class LabTestAdapter extends RecyclerView.Adapter<LabTestViewHolder> {

    private List<LabTest> labTestList;
    private Context context;

    public LabTestAdapter(List<LabTest> labTestList, Context context) {
        this.labTestList = labTestList;
        this.context = context;
    }

    @NonNull
    @Override
    public LabTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lab_test_view_holder,parent,false);
        return new LabTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabTestViewHolder holder, int position) {
        LabTest labTest = labTestList.get(position);
        holder.bind(labTest);
        holder.itemView.setOnClickListener(e -> {
            LabTest clickedItem = labTestList.get(position);
            File file = new File(clickedItem.path);

            // Generate content URI using FileProvider
            Uri fileUri = FileProvider.getUriForFile(context, "com.example.mypharmacy.fileprovider", file);

            Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
            openFileIntent.setDataAndType(fileUri, getMimeType(fileUri));
            openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            PackageManager pm = context.getPackageManager();
            if (openFileIntent.resolveActivity(pm) != null) {
                context.startActivity(openFileIntent);
            } else {
                // No app available to handle the file
                Toast.makeText(context, "No app available to open the file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getMimeType(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.getType(uri);
    }


    @Override
    public int getItemCount() {
        return labTestList.size();
    }

}
