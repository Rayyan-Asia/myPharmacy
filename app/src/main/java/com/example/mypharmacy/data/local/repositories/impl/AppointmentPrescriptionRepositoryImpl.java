package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.AppointmentPrescriptionDao;
import com.example.mypharmacy.data.local.daos.DoctorDao;
import com.example.mypharmacy.data.local.entities.AppointmentPrescription;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.AppointmentPrescriptionRepository;

import java.util.List;

public class AppointmentPrescriptionRepositoryImpl implements AppointmentPrescriptionRepository {
    private AppointmentPrescriptionDao appointmentPrescriptionDao;
    private Context context;

    myPharmacyDatabase database;

    public AppointmentPrescriptionRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.appointmentPrescriptionDao = database.getAppointmentPrescriptionDao();
    }
    @Override
    public long insert(AppointmentPrescription appointmentPrescription) {
        return appointmentPrescriptionDao.insert(appointmentPrescription);
    }

    @Override
    public void delete(AppointmentPrescription appointmentPrescription) {
        appointmentPrescriptionDao.delete(appointmentPrescription);
    }

    @Override
    public List<AppointmentPrescription> getPrescriptionsForAppointment(int appointmentId) {
        return appointmentPrescriptionDao.getPrescriptionsForAppointment(appointmentId);
    }

    @Override
    public List<AppointmentPrescription> getAppointmentsForPrescription(int prescriptionId) {
        return appointmentPrescriptionDao.getAppointmentsForPrescription(prescriptionId);
    }
}
