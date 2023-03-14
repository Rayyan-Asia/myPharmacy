package com.example.mypharmacy.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.mypharmacy.data.local.daos.PersonDao;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;

import javax.inject.Inject;

@Database(entities = {Person.class}, version = 1)
public abstract class myPharmacyDatabase extends RoomDatabase {

    public abstract PersonDao personDao();
    private static final String DB_NAME = "myPharmacy.db";

    @Inject
    PersonRepository personRepository;
    private static myPharmacyDatabase instance;

    public static synchronized myPharmacyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), myPharmacyDatabase.class, DB_NAME)
                    .build();
        }
        return instance;
    }
}



