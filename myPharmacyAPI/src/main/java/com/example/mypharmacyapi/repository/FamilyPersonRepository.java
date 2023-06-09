package com.example.mypharmacyapi.repository;

import com.example.mypharmacyapi.entity.Family;
import com.example.mypharmacyapi.entity.FamilyPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyPersonRepository extends JpaRepository<FamilyPerson, Long> {

}
