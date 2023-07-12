package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import android.graphics.Color;

import com.example.mypharmacy.R;
import com.example.mypharmacy.data.local.daos.MenstruationDao;
import com.example.mypharmacy.data.local.entities.Menstruation;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.MenstruationRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.Hashtable;
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
    public void updateMenstruation(Menstruation menstruation) {
        menstruationDao.updateMenstruation(menstruation);
    }

    @Override
    public Menstruation getMenstruation() {
        return menstruationDao.getMenstruation();
    }

    @Override
    public List<Menstruation> getMenstruations() {
        return menstruationDao.getMenstruations();
    }


    @Override
    public Hashtable<LocalDate, Integer> getCalendarDays() {
        List<Menstruation> menstruations = getMenstruations();
        Hashtable<LocalDate, Integer> dates= new Hashtable<>();
        for (Menstruation menstruation:menstruations) {
            LocalDate start = menstruation.startDate;
            LocalDate end = menstruation.endDate;
            while(start.isBefore(end) || start.isEqual(end)){
                dates.put(start, android.R.color.holo_red_light);
                start = start.plusDays(1);
            }
        }
        Menstruation menstruation = getMenstruation();
        LocalDate end = menstruation.endDate;
        int days = 1;
        while (days != 28){
            int color;
            if(days <= 5){
                color =  android.R.color.holo_green_light;
            } else if (days <= 11 ) {
                color = R.color.light_pink;
            }else if (days <= 23) {
                color = android.R.color.holo_blue_light;
            }else {
                color = android.R.color.darker_gray;
            }
            dates.put(end.plusDays(days), color);
            days++;
        }
        return dates;
    }
}
