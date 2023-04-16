package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Menstruation;

public interface MenstruationRepository {

    void insertMenstruation(Menstruation menstruation);
    Menstruation getMenstruation();

}
