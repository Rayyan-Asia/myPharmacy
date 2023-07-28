package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Person;

public interface PersonRepository {
    long insertPerson(Person person);
    Person getPerson();
    Person getPersonWithId(int id);
}
