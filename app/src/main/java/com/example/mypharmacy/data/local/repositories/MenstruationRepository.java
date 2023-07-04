package com.example.mypharmacy.data.local.repositories;

import android.graphics.Color;

import com.example.mypharmacy.data.local.entities.Menstruation;

import java.time.LocalDate;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public interface MenstruationRepository {

    void insertMenstruation(Menstruation menstruation);

    void updateMenstruation(Menstruation menstruation); // Add this method

    Menstruation getMenstruation();

    List<Menstruation> getMenstruations();

    Hashtable<LocalDate, Integer> getCalendarDays();
}