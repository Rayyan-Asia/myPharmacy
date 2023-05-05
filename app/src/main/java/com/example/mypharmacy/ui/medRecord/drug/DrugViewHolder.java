package com.example.mypharmacy.ui.medRecord.drug;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;

public class DrugViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView typeTextView;
    private TextView manufacturerTextView;
    private TextView descriptionTextView;
    private TextView expiryDateTextView;

    public DrugViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.nameTextView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
        typeTextView = itemView.findViewById(R.id.typeTextView);
        manufacturerTextView = itemView.findViewById(R.id.manufacturerTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
    }

    public void bind(Drug drug) {
        nameTextView.setText(drug.getName());
        categoryTextView.setText(drug.getCategory());
        typeTextView.setText(drug.getType());
        manufacturerTextView.setText(drug.getManufacturer());
        descriptionTextView.setText(drug.getDescription());
        expiryDateTextView.setText(drug.getExpiryDate().toString());
    }
}

