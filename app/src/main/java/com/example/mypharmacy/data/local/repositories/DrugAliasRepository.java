package com.example.mypharmacy.data.local.repositories;

import java.util.List;

public interface DrugAliasRepository {

    List<Integer> getAliasDrugIds(int givenDrugId);
}