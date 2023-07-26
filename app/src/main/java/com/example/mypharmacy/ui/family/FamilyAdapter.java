package com.example.mypharmacy.ui.family;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.FamilyMemeber;
import com.example.mypharmacy.data.local.entities.Person;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyViewHolder> {


    private List<FamilyMemeber> FamilyList;

    public FamilyAdapter(List<FamilyMemeber> FamilyList) {
        this.FamilyList = FamilyList;
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_family_view_holder, parent, false);
        return new FamilyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        FamilyMemeber person = FamilyList.get(position);
        holder.bind(person);
        holder.itemView.setOnClickListener(e -> {

        });
    }
    @Override
    public int getItemCount() {
        return FamilyList.size();
    }

}

