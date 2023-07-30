package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.DrugConflict;

import java.util.List;

public interface DrugConflictRepository {
    List<Integer> getConflictingDrugIds(int givenDrugId);

    long insert(DrugConflict drugConflict);
}
