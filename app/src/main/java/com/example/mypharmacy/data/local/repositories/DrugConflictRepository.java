package com.example.mypharmacy.data.local.repositories;

import java.util.List;

public interface DrugConflictRepository {
    List<Integer> getConflictingDrugIds(int givenDrugId);
}
