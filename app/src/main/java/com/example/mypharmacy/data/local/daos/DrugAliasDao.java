package com.example.mypharmacy.data.local.daos;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrugAliasDao {
    @Query("SELECT drug_id_1 AS drugId FROM drug_alias WHERE drug_id_2 = :givenDrugId UNION " +
            "SELECT drug_id_2 AS drugId FROM drug_alias WHERE drug_id_1 = :givenDrugId")
    List<Integer> getAliasDrugIds(int givenDrugId);
}
