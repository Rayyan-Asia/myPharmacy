package com.example.mypharmacy.data.local.repositories;

import androidx.lifecycle.LiveData;

public interface NameRepository {
    void saveName(String name);
    LiveData<String> getName();
}