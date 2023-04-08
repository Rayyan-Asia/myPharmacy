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
import com.example.mypharmacy.data.local.entities.*;
import com.example.mypharmacy.data.local.repositories.PersonRepository;

import javax.inject.Inject;

@Database(entities = {Person.class, Doctor.class, Drug.class, Prescription.class, PrescriptionDrug.class},
        autoMigrations = {@AutoMigration (from = 1, to = 2)},
        version = 2)
public abstract class myPharmacyDatabase extends RoomDatabase {
     private static final String DB_NAME = "myPharmacy.db";
     private static myPharmacyDatabase instance;


     public abstract PersonDao getPersonDao();

     public static synchronized myPharmacyDatabase getInstance(Context context) {
          if (instance == null) {
               instance = Room.databaseBuilder(context, myPharmacyDatabase.class, DB_NAME)
                       .build();
          }
          return instance;
     }

}







