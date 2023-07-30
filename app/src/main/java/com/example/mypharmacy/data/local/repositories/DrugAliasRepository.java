package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.DrugAlias;

import java.util.List;

public interface DrugAliasRepository {

    List<Integer> getAliasDrugIds(int givenDrugId);

    long insert(DrugAlias drugAlias);
}