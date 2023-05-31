package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.entities.Menstruation;

import java.util.List;

@Dao
public interface LabTestDao {
    @Query("Select * from lab_test where id == :id")
     LabTest findLabTest(int id);

    @Query("SELECT * FROM lab_test order by id desc LIMIT 20;")
    public List<LabTest> listLabTests();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertLabTest(LabTest labTest);

}
