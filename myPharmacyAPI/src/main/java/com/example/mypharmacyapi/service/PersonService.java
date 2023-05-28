package com.example.mypharmacyapi.service;

import com.example.mypharmacyapi.dto.PersonDto;
import com.example.mypharmacyapi.dto.PersonInsertDto;
import com.example.mypharmacyapi.entity.Person;

import java.util.Optional;

public interface PersonService {

    PersonDto insertPerson(PersonInsertDto person);

    PersonDto getPerson(long id);
}
