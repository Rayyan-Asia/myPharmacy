package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.AppointmentDao;
import com.example.mypharmacy.data.local.daos.DoctorDao;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;

public class DoctorRepositoryImpl implements DoctorRepository {
    private DoctorDao doctorDao;
    private Context context;

    myPharmacyDatabase database;

    public DoctorRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.doctorDao = database.getDoctorDao();
    }
    @Override
    public long insertDoctor(Doctor doctor) {
        return doctorDao.insertDoctor(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        return doctorDao.getDoctor(id);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        doctorDao.updateDoctor(doctor);
    }
}
