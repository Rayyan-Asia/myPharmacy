package com.example.mypharmacy.data.local.repositories.impl;

import android.content.Context;
import com.example.mypharmacy.data.local.daos.PersonDao;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.myPharmacyDatabase;
import com.example.mypharmacy.data.local.repositories.PersonRepository;

import java.util.concurrent.Executor;

public class PersonRepositoryImpl implements PersonRepository {


    private PersonDao personDao;
    private Context context;

    myPharmacyDatabase database;

    public PersonRepositoryImpl(Context context) {
        this.context = context;
        database = myPharmacyDatabase.getInstance(context);
        this.personDao = database.getPersonDao();
    }

    @Override
    public void insertPerson(Person person) {
        personDao.insertPerson(person);
    }

    @Override
    public Person getPerson() {
        return personDao.getPerson();
    }
}
