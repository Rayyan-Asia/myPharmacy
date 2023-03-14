package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import com.example.mypharmacy.data.local.entities.Person;

@Dao
public interface PersonDao {
        @Insert
         void insertPerson(Person person);


}
