package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.entities.AppointmentPrescription;
import com.example.mypharmacy.data.local.entities.Drug;

import java.util.List;

public interface AppointmentDrugRepository {
    long insert(AppointmentDrug appointmentDrug);
    List<AppointmentDrug> getAllAppointmentDrugs(int appointmentId);
}
