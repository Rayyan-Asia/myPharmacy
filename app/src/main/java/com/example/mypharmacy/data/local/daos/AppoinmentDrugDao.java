package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.entities.AppointmentPrescription;
import com.example.mypharmacy.data.local.entities.Drug;
import lombok.Data;

import java.util.List;

@Dao
public interface AppoinmentDrugDao {
        @Insert
        long insert(AppointmentDrug appoinmentDrug);

        @Query("SELECT * FROM appointment_drug where appointment_id=:appointmentId")
        List<AppointmentDrug> getAllAppointmentDrugs(int appointmentId);
}
