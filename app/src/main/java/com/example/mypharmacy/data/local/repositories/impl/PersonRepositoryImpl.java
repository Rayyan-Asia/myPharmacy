package com.example.mypharmacy.data.local.repositories.impl;

import com.example.mypharmacy.data.local.daos.PersonDao;
import com.example.mypharmacy.data.local.entities.Person;
import com.example.mypharmacy.data.local.repositories.PersonRepository;
import com.google.firebase.database.FirebaseDatabase;

public class PersonRepositoryImpl implements PersonRepository {

    private PersonDao personDao;
    public PersonRepositoryImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public void insertPerson(Person person) {
        personDao.insertPerson(person);
    }
}
