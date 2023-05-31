package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;

import com.example.mypharmacy.data.local.daos.LabTestDao;
import com.example.mypharmacy.data.local.daos.MenstruationDao;
import com.example.mypharmacy.data.local.entities.LabTest;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;

import java.util.List;

public class LabTestRepositoryImpl implements LabTestRepository {

    private LabTestDao labTestDao;
    private Context context;

    myPharmacyDatabase database;

    public LabTestRepositoryImpl(Context context){
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.labTestDao = database.getLabTestDao();
    }


    @Override
    public LabTest findLabTest(int id) {
        return labTestDao.findLabTest(id);
    }

    @Override
    public List<LabTest> listLabTests() {
        return labTestDao.listLabTests();
    }

    @Override
    public void insertLabTest(LabTest labTest) {
        labTestDao.insertLabTest(labTest);
    }
}
