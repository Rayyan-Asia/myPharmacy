package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import com.example.mypharmacy.data.local.entities.Person;

@Dao
public interface PersonDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertPerson(Person person);
}
