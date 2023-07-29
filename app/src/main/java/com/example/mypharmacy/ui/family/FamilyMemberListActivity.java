package com.example.mypharmacy.ui.family;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;
import com.example.mypharmacy.ui.medRecord.labTest.SpacingItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FamilyMemberListActivity extends AppCompatActivity {

    private FamilyRepository familyRepository;
    private RecyclerView familyMemberRecyclerView;
    private FloatingActionButton addFamilyMember;

    private Context context;

    private int familyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_list);
        familyId = (int) getIntent().getExtras().get("familyId");

        context = this;
        familyRepository = new FamilyRepositoryImpl(this);
        familyMemberRecyclerView = findViewById(R.id.family_member_recycler_view);
        int spacingInPixels = 20;
        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(spacingInPixels);
        familyMemberRecyclerView.addItemDecoration(itemDecoration);

        addFamilyMember = findViewById(R.id.add_family_member_button);
        addFamilyMember.setOnClickListener(e->{
            Intent intent = new Intent(this, CreateFamilyMemberActivity.class);
            intent.putExtra("familyId",familyId);
            startActivity(intent);
        });
        new LoadFamilyMemberListAsyncTask(context, familyMemberRecyclerView,familyId).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadFamilyMemberListAsyncTask(context, familyMemberRecyclerView,familyId).execute();
    }
}