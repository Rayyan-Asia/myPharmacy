package com.example.mypharmacy.ui.medRecord.drug;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.DrugAliasRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;

import java.util.List;

public class DrugViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView typeTextView;
    private TextView manufacturerTextView;
    private TextView descriptionTextView;
    private TextView expiryDateTextView;

    private Context context;
    private List<Drug> drugList;

    public DrugViewHolder(View itemView, Context context, List<Drug> drugList) {
        super(itemView);
        this.context = context;
        this.drugList = drugList;
        nameTextView = itemView.findViewById(R.id.nameTextView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
        typeTextView = itemView.findViewById(R.id.typeTextView);
        manufacturerTextView = itemView.findViewById(R.id.manufacturerTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
        itemView.setOnClickListener(this);
    }

    public void bind(Drug drug) {
        nameTextView.setText(drug.getName());
        categoryTextView.setText(drug.getCategory());
        typeTextView.setText(drug.getType());
        manufacturerTextView.setText(drug.getManufacturer());
        descriptionTextView.setText(drug.getDescription());
        expiryDateTextView.setText(drug.getExpiryDate().toString());
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            Drug drug = drugList.get(position);

            // Run the repository part in a separate thread using AsyncTask
            DrugAliasAsyncTask task = new DrugAliasAsyncTask(context, drug);
            task.execute();
        }
    }







}

