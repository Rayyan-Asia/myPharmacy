package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Menstruation;

import java.util.List;

public interface MenstruationRepository {

    void insertMenstruation(Menstruation menstruation);
    Menstruation getMenstruation();

    List<Menstruation> getMenstruations();

}
