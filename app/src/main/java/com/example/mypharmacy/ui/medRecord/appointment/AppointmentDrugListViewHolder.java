package com.example.mypharmacy.ui.medRecord.appointment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.repositories.AppointmentDrugRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentDrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugAliasRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.drug.DrugAliasAsyncTask;

import java.util.List;

public class AppointmentDrugListViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView typeTextView;
    private TextView manufacturerTextView;
    private TextView descriptionTextView;
    private TextView expiryDateTextView;
private DrugRepository drugRepository;
    private Context context;
    private List<Drug> drugList;

    public AppointmentDrugListViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.drugList = drugList;
        nameTextView = itemView.findViewById(R.id.nameTextView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
        typeTextView = itemView.findViewById(R.id.typeTextView);
        manufacturerTextView = itemView.findViewById(R.id.manufacturerTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        expiryDateTextView = itemView.findViewById(R.id.expiryDateTextView);
        drugRepository =  new DrugRepositoryImpl(itemView.getContext());
    }

    public void bind(AppointmentDrug appointmentDrug) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drug drug = drugRepository.getDrug(appointmentDrug.getDrugId());
                nameTextView.setText(drug.getName());
                categoryTextView.setText(drug.getCategory());
                typeTextView.setText(drug.getType());
                manufacturerTextView.setText(drug.getManufacturer());
                descriptionTextView.setText(drug.getDescription());
                expiryDateTextView.setText(drug.getExpiryDate().toString());
            }
        }).start();
    }

}
