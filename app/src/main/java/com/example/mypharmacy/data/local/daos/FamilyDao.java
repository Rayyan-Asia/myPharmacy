package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mypharmacy.data.local.entities.Family;

import java.util.List;

@Dao
public interface FamilyDao {
    @Insert
    void insert(Family family);

    @Update
    void update(Family family);

    @Delete
    void delete(Family family);

    @Query("SELECT * FROM family")
    List<Family> getAllFamilies();

    @Query("SELECT * FROM family WHERE id = :familyId")
    Family getFamilyById(int familyId);
}
