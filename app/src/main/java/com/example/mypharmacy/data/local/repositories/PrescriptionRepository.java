package com.example.mypharmacy.data.local.repositories;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.mypharmacy.data.local.entities.Prescription;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository {
    long insertPrescription(Prescription prescription);
    Prescription getPrescriptionById(int prescriptionId);
    List<Prescription> getPrescriptionsByDoctorId(int doctorId);
    List<Prescription> getActivePrescriptions(LocalDate date);
    void deletePrescription(int prescriptionId);
    void updatePrescription(Prescription prescription);
}
