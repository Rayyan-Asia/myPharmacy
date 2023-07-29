package com.example.mypharmacy.ui.family;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.labTest.SpacingItemDecoration;

import java.util.List;

public class LoadFamilyListAsyncTask extends AsyncTask<Void, Void, List<Family>> {
    private FamilyRepository familyRepository;
    private RecyclerView familyRecyclerView;
    private Context context;
    private List<Family> families;

    public LoadFamilyListAsyncTask(Context context, RecyclerView familyRecyclerView) {
        this.context = context;
        this.familyRecyclerView = familyRecyclerView;
        familyRepository = new FamilyRepositoryImpl(context);
    }

    @Override
    protected List<Family> doInBackground(Void... voids) {
        // Load the family list from the repository in the background
        return familyRepository.getAllFamilies();
    }

    @Override
    protected void onPostExecute(List<Family> families) {
        super.onPostExecute(families);
        // Update the RecyclerView with the loaded family list
        this.families = families;
        if (families.size() == 0) {
            // Start the CreateFamilyActivity if families list has size 0
            Intent intent = new Intent(context, CreateFamilyActivity.class);
            context.startActivity(intent);
        } else {
            // Update the RecyclerView with the loaded family list
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        FamilyAdapter familyAdapter = new FamilyAdapter(families, context);
        familyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        familyRecyclerView.setAdapter(familyAdapter);
    }
}
