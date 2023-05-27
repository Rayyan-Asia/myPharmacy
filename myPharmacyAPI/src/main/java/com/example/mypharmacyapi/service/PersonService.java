package com.example.mypharmacyapi.service;

import com.example.mypharmacyapi.entity.Person;

public interface PersonService {

    long insertPerson(Person person);

    Person getPerson();
}
