package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.AppoinmentDrugDao;
import com.example.mypharmacy.data.local.daos.AppointmentPrescriptionDao;
import com.example.mypharmacy.data.local.entities.AppointmentDrug;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.AppointmentDrugRepository;

import java.util.List;

public class AppointmentDrugRepositoryImpl implements AppointmentDrugRepository {
    private AppoinmentDrugDao appointmentDrugDao;
    private Context context;

    myPharmacyDatabase database;

    public AppointmentDrugRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.appointmentDrugDao = database.getAppointmentDrugDao();
    }

    @Override
    public long insert(AppointmentDrug appointmentDrug) {
        return appointmentDrugDao.insert(appointmentDrug);
    }

    @Override
    public List<AppointmentDrug> getAllAppointmentDrugs(int appointmentId) {
        return appointmentDrugDao.getAllAppointmentDrugs(appointmentId);
    }
}
