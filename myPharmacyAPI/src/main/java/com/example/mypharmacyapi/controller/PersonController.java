package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.PersonDto;
import com.example.mypharmacyapi.dto.PersonInsertDto;
import com.example.mypharmacyapi.entity.Person;
import com.example.mypharmacyapi.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PersonController {
    private final PersonService personService;
    @PostMapping
    ResponseEntity<PersonDto> insertPerson(@Valid @RequestBody PersonInsertDto person){
        return ResponseEntity.ok().body(personService.insertPerson(person));
    }
    @GetMapping("/{id}")
    ResponseEntity<PersonDto> getPerson(@PathVariable long id){
        return ResponseEntity.ok().body(personService.getPerson(id));
    }
}
