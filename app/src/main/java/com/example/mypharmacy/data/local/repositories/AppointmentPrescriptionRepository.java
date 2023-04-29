package com.example.mypharmacy.data.local.repositories;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mypharmacy.data.local.entities.AppointmentPrescription;

import java.util.List;

public interface AppointmentPrescriptionRepository {
    long insert(AppointmentPrescription appointmentPrescription);

    void delete(AppointmentPrescription appointmentPrescription);
    List<AppointmentPrescription> getPrescriptionsForAppointment(int appointmentId);

    List<AppointmentPrescription> getAppointmentsForPrescription(int prescriptionId);
}
