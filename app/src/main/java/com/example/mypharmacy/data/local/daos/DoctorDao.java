package com.example.mypharmacy.data.local.daos;

import androidx.room.*;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDoctor(Doctor doctor);
    @Query("SELECT * FROM Doctor where id=:id")
    Doctor getDoctor(int id);
    @Update
    void updateDoctor(Doctor doctor);
    @Query("SELECT * FROM doctor ORDER BY name ASC")
    List<Doctor> getAllDoctors();
}
