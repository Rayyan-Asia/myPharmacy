package com.example.mypharmacy.data.local.repositories.impl;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.mypharmacy.data.local.repositories.NameRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.*;
import android.util.Log;

public class FirebaseNameRepository implements NameRepository {
    private DatabaseReference databaseRef;

    public FirebaseNameRepository() {
        databaseRef = FirebaseDatabase.getInstance().getReference("name");
    }

    @Override
    public void saveName(String name) {
        databaseRef.setValue(name);
    }

    @Override
    public LiveData<String> getName() {
        MutableLiveData<String> nameLiveData = new MutableLiveData<>();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                if (name != null) {
                    nameLiveData.setValue(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseNameRepository", "Error fetching name from Firebase", error.toException());
            }
        });
        return nameLiveData;
    }
}

