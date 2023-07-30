package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;

import com.example.mypharmacy.data.local.daos.DrugAliasDao;
import com.example.mypharmacy.data.local.entities.DrugAlias;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.DrugAliasRepository;

import java.util.List;

public class DrugAliasRepositoryImpl implements DrugAliasRepository {
    private DrugAliasDao drugAliasDao;

    private Context context;

    myPharmacyDatabase database;

    public DrugAliasRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.drugAliasDao = database.getDrugAliasDao();
    }

    @Override
    public List<Integer> getAliasDrugIds(int givenDrugId) {
        return drugAliasDao.getAliasDrugIds(givenDrugId);
    }

    @Override
    public long insert(DrugAlias drugAlias) {
        return drugAliasDao.insert(drugAlias);
    }
}
