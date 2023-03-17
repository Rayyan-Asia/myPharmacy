package com.example.mypharmacy.data.local.repositories;

import com.example.mypharmacy.data.local.entities.Person;

public interface PersonRepository {
    void insertPerson(Person person);
    Person getPerson();
}
