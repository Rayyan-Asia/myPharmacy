package com.example.mypharmacy.data.local.repositories.impl;

import android.app.Application;
import android.content.Context;
import com.example.mypharmacy.data.local.daos.DoctorDao;
import com.example.mypharmacy.data.local.daos.PrescriptionDao;
import com.example.mypharmacy.data.local.entities.Prescription;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.PrescriptionRepository;

import java.time.LocalDate;
import java.util.List;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    private PrescriptionDao prescriptionDao;
    private Context context;

    myPharmacyDatabase database;

    public PrescriptionRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.prescriptionDao = database.getPrescriptionDao();
    }

    @Override
    public long insertPrescription(Prescription prescription) {
        return prescriptionDao.insertPrescription(prescription);
    }

    @Override
    public Prescription getPrescriptionById(int prescriptionId) {
        return prescriptionDao.getPrescriptionById(prescriptionId);
    }

    @Override
    public List<Prescription> getPrescriptionsByDoctorId(int doctorId) {
        return prescriptionDao.getPrescriptionsByDoctorId(doctorId);
    }

    @Override
    public List<Prescription> getActivePrescriptions(LocalDate date) {
        return prescriptionDao.getActivePrescriptions(date);
    }

    @Override
    public void deletePrescription(int prescriptionId) {
        prescriptionDao.deletePrescription(prescriptionId);
    }

    @Override
    public void updatePrescription(Prescription prescription) {
        prescriptionDao.updatePrescription(prescription);
    }
}
