package com.example.mypharmacyapi.repository;

import com.example.mypharmacyapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
