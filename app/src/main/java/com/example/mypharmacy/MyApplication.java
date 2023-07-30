package com.example.mypharmacy;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.annotation.UiThread;

import com.example.mypharmacy.data.local.entities.Drug;
import com.example.mypharmacy.data.local.entities.DrugAlias;
import com.example.mypharmacy.data.local.entities.DrugConflict;
import com.example.mypharmacy.data.local.entities.Family;
import com.example.mypharmacy.data.local.entities.FamilyMember;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.AppointmentRepository;
import com.example.mypharmacy.data.local.repositories.DoctorRepository;
import com.example.mypharmacy.data.local.repositories.DrugAliasRepository;
import com.example.mypharmacy.data.local.repositories.DrugConflictRepository;
import com.example.mypharmacy.data.local.repositories.DrugRepository;
import com.example.mypharmacy.data.local.repositories.FamilyRepository;
import com.example.mypharmacy.data.local.repositories.LabTestRepository;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.example.mypharmacy.data.local.repositories.impl.AppointmentRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DoctorRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugAliasRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugConflictRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.DrugRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.FamilyRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.LabTestRepositoryImpl;
import com.example.mypharmacy.data.local.repositories.impl.PersonRepositoryImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

// appComponent lives in the Application class to share its lifecycle
public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
    }


}

