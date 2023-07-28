package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyViewHolder> {

    private List<Family>  familyList;
    private Context context;

    FamilyAdapter(List<Family> list, Context context){
        this.familyList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_family_view_holder,parent,false);
        return new FamilyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        Family family = familyList.get(position);
        holder.bind(family);
        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(this.context,FamilyMemberListActivity.class);
            intent.putExtra("familyId",family.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return familyList.size();
    }
}
