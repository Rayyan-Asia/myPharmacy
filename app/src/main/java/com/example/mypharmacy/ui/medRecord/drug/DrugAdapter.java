package com.example.mypharmacy.ui.medRecord.drug;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.ui.medRecord.doctor.DoctorViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter<DrugViewHolder>{

    private List<Drug> drugList;

    public DrugAdapter(List<Drug> drugList) {
        this.drugList = drugList;
    }
    @NonNull
    @NotNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_drug_card, parent, false);
        return new DrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DrugViewHolder holder, int position) {
        Drug drug = drugList.get(position);
        holder.bind(drug);
        holder.itemView.setOnClickListener(e -> {

        });
    }
    public void updateData(List<Drug> data) {
        drugList = data;
    }

    @Override
    public int getItemCount() {
        return drugList.size();
    }
}
