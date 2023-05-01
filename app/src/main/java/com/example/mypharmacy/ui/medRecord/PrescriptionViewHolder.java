package com.example.mypharmacy.ui.medRecord;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Appointment;
import com.example.mypharmacy.data.local.entities.Prescription;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.PrescriptionRepository;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PrescriptionRepositoryImpl;

public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView prescriptionNameView;
    private TextView prescriptionDrugNameView;
    public PrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        this.prescriptionNameView = itemView.findViewById(R.id.prescriptionNameCardView);
        this.prescriptionDrugNameView = itemView.findViewById(R.id.prescriptionDrugNameCardView);
    }

    public void bind(Prescription prescription) {
        PrescriptionRepository prescriptionRepository = new PrescriptionRepositoryImpl(context);
        DrugRepository drugRepository = new DrugRepositoryImpl(context);
        // Set the text for the TextViews based on the Appointment object
        new Thread(new Runnable() {
            @Override
            public void run() {
                prescriptionNameView.setText(prescription.getName());
                prescriptionDrugNameView.setText(drugRepository.getDrug(prescription.getDrugId()).getName());
            }
        }).start();
    }
}
