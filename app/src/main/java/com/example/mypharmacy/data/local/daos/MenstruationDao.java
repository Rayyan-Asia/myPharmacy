package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mypharmacy.data.local.entities.Menstruation;

import java.util.List;

@Dao
public interface MenstruationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMenstruation(Menstruation menstruation);

    @Update
    void updateMenstruation(Menstruation menstruation);

    @Query("SELECT * FROM Menstruation ORDER BY id DESC LIMIT 1;")
    Menstruation getMenstruation();

    @Query("SELECT * FROM Menstruation ORDER BY id DESC LIMIT 200;")
    List<Menstruation> getMenstruations();
}