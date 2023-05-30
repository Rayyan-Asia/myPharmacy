package com.example.mypharmacy.ui.medRecord.labTest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.LabTest;

import java.util.List;

public class LabTestAdapter extends RecyclerView.Adapter<LabTestViewHolder> {

    private List<LabTest> labTestList;

    public LabTestAdapter(List<LabTest> labTestList) {
        this.labTestList = labTestList;
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
        holder.itemView.setOnClickListener(e->{

        });
    }

    public void updateData(List<LabTest> data){
        labTestList = data;
    }

    @Override
    public int getItemCount() {
        return labTestList.size();
    }

}
