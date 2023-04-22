package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mypharmacy.data.local.entities.AppointmentPrescription;

import java.util.List;

@Dao
public interface AppointmentPrescriptionDao {
    @Insert
    long insert(AppointmentPrescription appointmentPrescription);

    @Delete
    void delete(AppointmentPrescription appointmentPrescription);

    @Query("SELECT * FROM appointment_prescription WHERE appointment_id = :appointmentId")
    List<AppointmentPrescription> getPrescriptionsForAppointment(int appointmentId);

    @Query("SELECT * FROM appointment_prescription WHERE prescription_id = :prescriptionId")
    List<AppointmentPrescription> getAppointmentsForPrescription(int prescriptionId);
}

