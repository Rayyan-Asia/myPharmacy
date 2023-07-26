package com.example.mypharmacy.data.local.repositories.impl;


import android.content.Context;
import com.example.mypharmacy.data.local.daos.DrugConflictDao;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.DrugConflictRepository;
import java.util.List;

public class DrugConflictRepositoryImpl implements DrugConflictRepository {
    private DrugConflictDao drugConflictDao;

    public DrugConflictRepositoryImpl(Context context) {
        myPharmacyDatabase database = myPharmacyDatabase.getInstance(context);
        this.drugConflictDao = database.getDrugConflictDao();
    }

    @Override
    public List<Integer> getConflictingDrugIds(int givenDrugId) {
        return drugConflictDao.getConflictingDrugIds(givenDrugId);
    }
}
