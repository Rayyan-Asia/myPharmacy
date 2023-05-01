package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;

import com.example.mypharmacy.data.local.daos.MenstruationDao;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;

import java.util.List;

public class MenstruationRepositoryImpl implements MenstruationRepository{


    private MenstruationDao menstruationDao;
    private Context context;

    myPharmacyDatabase database;

    public MenstruationRepositoryImpl(Context context){
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.menstruationDao = database.getMenstruationDao();
    }

    @Override
    public void insertMenstruation(Menstruation menstruation) {
        menstruationDao.insertMenstruation(menstruation);
    }

    @Override
    public Menstruation getMenstruation() {
        return menstruationDao.getMenstruation();
    }

    @Override
    public List<Menstruation> getMenstruations() {
        return menstruationDao.getMenstruations();
    }


}
