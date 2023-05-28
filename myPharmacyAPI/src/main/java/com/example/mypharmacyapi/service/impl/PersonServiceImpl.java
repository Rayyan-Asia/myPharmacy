package com.example.mypharmacyapi.service.impl;

import com.example.mypharmacyapi.dto.PersonDto;
import com.example.mypharmacyapi.dto.PersonInsertDto;
import com.example.mypharmacyapi.entity.Person;
import com.example.mypharmacyapi.repository.PersonRepository;
import com.example.mypharmacyapi.service.PersonService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private ModelMapper modelMapper;
    @Override
    public PersonDto insertPerson(PersonInsertDto personInsertDto) {
        Person person = modelMapper.map(personInsertDto, Person.class);
        Person newPerson = personRepository.save(person);
        return modelMapper.map(newPerson, PersonDto.class);
    }

    @Override
    public PersonDto getPerson(long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            return modelMapper.map(person, PersonDto.class);
        } else {
            return null;
        }
    }
}
