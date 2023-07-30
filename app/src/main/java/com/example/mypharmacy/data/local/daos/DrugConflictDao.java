package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mypharmacy.data.local.entities.DrugConflict;
import lombok.ToString;

import java.util.List;

@Dao
public interface DrugConflictDao {
    // Query to get drug IDs that are in conflict with the given drug ID
    @Query("SELECT drug_id_1 AS drugId FROM drug_conflict WHERE drug_id_2 = :givenDrugId UNION " +
            "SELECT drug_id_2 AS drugId FROM drug_conflict WHERE drug_id_1 = :givenDrugId")
    List<Integer> getConflictingDrugIds(int givenDrugId);
@Insert
    long insert(DrugConflict drugConflict);
}
