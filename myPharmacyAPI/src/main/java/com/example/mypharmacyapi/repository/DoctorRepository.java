package com.example.mypharmacyapi.repository;

import com.example.mypharmacyapi.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
