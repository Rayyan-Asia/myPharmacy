package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;

import java.util.List;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberViewHolder> {

    private List<FamilyMember> familyMemberList;
    private Context context;

    FamilyMemberAdapter(List<FamilyMember> list, Context context){
        this.familyMemberList = list;
        this.context = context;
    }
    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_family_member_view_holder,parent,false);
        return new FamilyMemberViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        FamilyMember familyMember = familyMemberList.get(position);
        holder.bind(familyMember);
        holder.itemView.setOnClickListener(e->{
            Intent intent = new Intent(context, FamilyMemberAppointmentsActivity.class);
            intent.putExtra("personId",familyMember.personId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return familyMemberList.size();
    }
}
