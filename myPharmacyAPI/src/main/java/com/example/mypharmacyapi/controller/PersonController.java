package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.PersonDto;
import com.example.mypharmacyapi.dto.PersonInsertDto;
import com.example.mypharmacyapi.entity.Person;
import com.example.mypharmacyapi.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {
    private PersonService personService;
    @PostMapping("person")
    PersonDto insertPerson(@Valid @RequestBody PersonInsertDto person){
        return personService.insertPerson(person);
    }
    @GetMapping("person/{id}")
    PersonDto getPerson(@PathVariable long id){
        return personService.getPerson(id);
    }
}
