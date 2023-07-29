package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;

import java.io.File;

public class FamilyViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView name;
    private ImageView imageView;

    public FamilyViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        name = itemView.findViewById(R.id.family_name_text);
        imageView = itemView.findViewById(R.id.file_view_family);
    }
    public void bind(Family family) {
        name.setText(family.name);
        File file = new File(family.profilePicPath);
        if (file.exists()) {
            Glide.with(context)
                    .load(file) // Assuming the image path is stored in the 'path' variable of the LabTest object
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageView);
        } else {
            Log.e("ERROR", "FILE NOT FOUND!!!! " + family.profilePicPath);
        }

    }
}
