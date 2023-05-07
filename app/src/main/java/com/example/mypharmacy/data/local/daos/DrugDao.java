package com.example.mypharmacy.data.local.daos;

import androidx.room.*;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;

import java.util.List;

@Dao
public interface DrugDao {
    @Query("SELECT * FROM drug ORDER BY name ASC")
    List<Drug> getAllDrugs();
    @Query("SELECT * FROM drug where id=:id")
    Drug getDrug(int id);

    @Insert
    long insertDrug(Drug drug);
}
