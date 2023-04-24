package com.example.mypharmacy.data.local.daos;

import androidx.room.*;
import com.example.mypharmacy.data.local.entities.Converters;
import com.example.mypharmacy.data.local.entities.Prescription;

import java.time.LocalDate;
import java.util.List;

@Dao
@TypeConverters(Converters.class)
public interface PrescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPrescription(Prescription prescription);
    @Query("SELECT * FROM prescription WHERE id = :prescriptionId")
    Prescription getPrescriptionById(int prescriptionId);
    @Query("SELECT * FROM prescription WHERE doctor_id = :doctorId")
    List<Prescription> getPrescriptionsByDoctorId(int doctorId);
    @Query("SELECT * FROM prescription WHERE start_date <= :date AND end_date >= :date")
    List<Prescription> getActivePrescriptions(LocalDate date);
    @Query("DELETE FROM prescription WHERE id = :prescriptionId")
    void deletePrescription(int prescriptionId);
    @Update
    void updatePrescription(Prescription prescription);
}

