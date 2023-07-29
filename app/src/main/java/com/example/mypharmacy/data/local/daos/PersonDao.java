package com.example.mypharmacy.data.local.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mypharmacy.data.local.entities.Person;

@Dao
public interface PersonDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insertPerson(Person person);

        @Query("SELECT * FROM Person WHERE id == 1 LIMIT 1;")
        Person getPerson();
        @Query("SELECT * FROM Person WHERE id == :id LIMIT 1;")
        Person getPersonWithId(int id);
}
