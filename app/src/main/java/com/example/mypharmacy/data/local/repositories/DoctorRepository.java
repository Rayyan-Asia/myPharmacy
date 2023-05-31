package com.example.mypharmacy.data.local.repositories;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mypharmacy.data.local.entities.Doctor;

import java.util.List;

public interface DoctorRepository {
    long insertDoctor(Doctor doctor);
    Doctor getDoctor(int id);
    void updateDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
}
