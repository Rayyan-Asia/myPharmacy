package com.example.mypharmacy.data.local;

import android.content.ContentValues;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.mypharmacy.data.local.daos.PersonDao;
import com.example.mypharmacy.data.local.entities.*;
import com.example.mypharmacy.data.local.repositories.PersonRepository;

import javax.inject.Inject;
import java.time.LocalDate;

@Database(entities = {Person.class, Doctor.class, Drug.class, Prescription.class, PrescriptionDrug.class},
        version = 3, autoMigrations = @AutoMigration(from = 1, to = 2))
public abstract class myPharmacyDatabase extends RoomDatabase {
     private static final String DB_NAME = "myPharmacy.db";
     private static myPharmacyDatabase instance;


     public abstract PersonDao getPersonDao();

     public static synchronized myPharmacyDatabase getInstance(Context context) {
          if (instance == null) {
               instance = Room.databaseBuilder(context, myPharmacyDatabase.class, DB_NAME)
                       .addMigrations(SEED_DRUG_TABLE)
                       .build();
          }
          return instance;
     }
     public static final Migration SEED_DRUG_TABLE = new Migration(2, 3) {
          @Override
          public void migrate(SupportSQLiteDatabase database) {
               database.execSQL("CREATE TABLE drug_new (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, manufacturer TEXT, category TEXT, type TEXT, expiry_date INTEGER)");

               LocalDate now = LocalDate.now();

               ContentValues values = new ContentValues();
               values.put("name", "Amoxicillin");
               values.put("description", "An antibiotic used to treat bacterial infections");
               values.put("manufacturer", "Pfizer");
               values.put("category", "Antibiotic");
               values.put("type", "Capsule");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Paracetamol");
               values.put("description", "An analgesic used to relieve pain and reduce fever");
               values.put("manufacturer", "Johnson & Johnson");
               values.put("category", "Analgesic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Lisinopril-AstraZeneca");
               values.put("description", "An antihypertensive used to treat high blood pressure");
               values.put("manufacturer", "AstraZeneca");
               values.put("category", "Antihypertensive");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Ibuprofen-Bayer");
               values.put("description", "An analgesic used to relieve pain and reduce fever");
               values.put("manufacturer", "Bayer");
               values.put("category", "Analgesic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Ceftriaxone");
               values.put("description", "An antibiotic used to treat bacterial infections");
               values.put("manufacturer", "Roche");
               values.put("category", "Antibiotic");
               values.put("type", "Injection");
               values.put("expiry_date", now.plusYears(1).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Omeprazole-Pfizer");
               values.put("description", "A proton-pump inhibitor used to treat gastroesophageal reflux disease");
               values.put("manufacturer", "Pfizer");
               values.put("category", "Gastrointestinal");
               values.put("type", "Capsule");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Metformin-Merck");
               values.put("description", "An oral medication used to treat type 2 diabetes");
               values.put("manufacturer", "Merck");
               values.put("category", "Antidiabetic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Doxycycline");
               values.put("description", "An antibiotic used to treat bacterial infections");
               values.put("manufacturer", "Novartis");
               values.put("category", "Antibiotic");
               values.put("type", "Capsule");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Alprazolam");
               values.put("description", "A benzodiazepine used to treat anxiety disorders");
               values.put("manufacturer", "Mylan");
               values.put("category", "Anxiolytic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(1).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Atorvastatin");
               values.put("description", "A statin used to lower cholesterol levels");
               values.put("manufacturer", "Aurobindo Pharma");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Metformin-Teva");
               values.put("description", "An oral medication for type 2 diabetes");
               values.put("manufacturer", "Teva Pharmaceutical Industries");
               values.put("category", "Antidiabetic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Ibuprofen-Pfizer");
               values.put("description", "A nonsteroidal anti-inflammatory drug (NSAID)");
               values.put("manufacturer", "Pfizer");
               values.put("category", "Analgesic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(1).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Omeprazole-Dr.Reddy's");
               values.put("description", "A proton pump inhibitor used to treat stomach ulcers and acid reflux");
               values.put("manufacturer", "Dr. Reddy's Laboratories");
               values.put("category", "Gastrointestinal");
               values.put("type", "Capsule");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Levothyroxine");
               values.put("description", "A synthetic form of thyroid hormone used to treat hypothyroidism");
               values.put("manufacturer", "Takeda Pharmaceutical Company");
               values.put("category", "Endocrine");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Lisinopril-Apotex");
               values.put("description", "An ACE inhibitor used to treat hypertension and heart failure");
               values.put("manufacturer", "Apotex");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Warfarin");
               values.put("description", "An anticoagulant used to prevent blood clots");
               values.put("manufacturer", "Sandoz");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(1).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Citalopram");
               values.put("description", "A selective serotonin reuptake inhibitor (SSRI) used to treat depression and anxiety disorders");
               values.put("manufacturer", "Mylan");
               values.put("category", "Psychotropic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Losartan");
               values.put("description", "An angiotensin receptor blocker (ARB) used to treat hypertension and heart failure");
               values.put("manufacturer", "Aurobindo Pharma");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Simvastatin");
               values.put("description", "A statin used to lower cholesterol and prevent heart disease");
               values.put("manufacturer", "Teva Pharmaceutical Industries");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Rosuvastatin");
               values.put("description", "A statin used to lower cholesterol and prevent heart disease");
               values.put("manufacturer", "AstraZeneca");
               values.put("category", "Cardiovascular");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(3).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Cefuroxime");
               values.put("description", "A cephalosporin antibiotic used to treat bacterial infections");
               values.put("manufacturer", "Dr. Reddy's Laboratories");
               values.put("category", "Antibiotic");
               values.put("type", "Injection");
               values.put("expiry_date", now.plusYears(1).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               values = new ContentValues();
               values.put("name", "Erythromycin");
               values.put("description", "A macrolide antibiotic used to treat bacterial infections");
               values.put("manufacturer", "Sandoz");
               values.put("category", "Antibiotic");
               values.put("type", "Tablet");
               values.put("expiry_date", now.plusYears(2).toEpochDay());
               database.insert("drug_new", OnConflictStrategy.REPLACE, values);

               database.execSQL("DROP TABLE drug");
               database.execSQL("ALTER TABLE drug_new RENAME TO drug");
          }
     };


}







