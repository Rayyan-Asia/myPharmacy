package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;

import java.util.List;

public class LoadFamilyMemberListAsyncTask extends AsyncTask<Void, Void, List<FamilyMember>> {

    private FamilyRepository familyRepository;
    private RecyclerView familyMemberRecyclerView;
    private Context context;

    private int familyId;

    private List<FamilyMember> familyMembers;

    public LoadFamilyMemberListAsyncTask(Context context, RecyclerView familyMemberRecyclerView, int familyId) {
        this.context = context;
        this.familyMemberRecyclerView = familyMemberRecyclerView;
        familyRepository = new FamilyRepositoryImpl(context);
        this.familyId = familyId;
    }

    @Override
    protected List<FamilyMember> doInBackground(Void... voids) {
        return familyRepository.getAllFamilyMembersByFamilyId(familyId);
    }

    @Override
    protected void onPostExecute(List<FamilyMember> familyMembers) {
        super.onPostExecute(familyMembers);
        // Update the RecyclerView with the loaded family list
        this.familyMembers = familyMembers;
        if (familyMembers.size() == 0) {
            // Start the CreateFamilyActivity if families list has size 0
            Intent intent = new Intent(context, CreateFamilyActivity.class);
            context.startActivity(intent);
        } else {
            // Update the RecyclerView with the loaded family list
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        FamilyMemberAdapter familyAdapter = new FamilyMemberAdapter(familyMembers, context);
        familyMemberRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        familyMemberRecyclerView.setAdapter(familyAdapter);
    }
}
