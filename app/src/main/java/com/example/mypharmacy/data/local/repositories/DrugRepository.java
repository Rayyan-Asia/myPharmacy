package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Drug;

import java.util.List;

public interface DrugRepository {
    List<Drug> getAllDrugs();
    Drug getDrug(int id);
}
