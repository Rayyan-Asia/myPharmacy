package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.AppointmentDao;
import com.example.mypharmacy.data.local.daos.DoctorDao;
import com.example.mypharmacy.data.local.daos.DrugDao;
import com.example.mypharmacy.data.local.entities.Doctor;
import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;

import java.util.List;

public class DrugRepositoryImpl implements DrugRepository {
    private DrugDao drugDao;
    private Context context;

    myPharmacyDatabase database;

    public DrugRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.drugDao = database.getDrugDao();
    }
    @Override
    public List<Drug> getAllDrugs() { return drugDao.getAllDrugs();}

    @Override
    public Drug getDrug(int id) {
        return drugDao.getDrug(id);
    }

    @Override
    public long insertDrug(Drug drug) {
        return drugDao.insertDrug(drug);
    }

}
