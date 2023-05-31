package com.example.mypharmacy.ui.medRecord.labTest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;

public class LabTestViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private TextView title;
    private TextView date;


    public LabTestViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        title = itemView.findViewById(R.id.test_card_title);
        date = itemView.findViewById(R.id.test_card_date);
    }

    public void bind(LabTest labTest){
        title.setText(labTest.testName);
        date.setText(labTest.dateOfTest.toString());
    }


}