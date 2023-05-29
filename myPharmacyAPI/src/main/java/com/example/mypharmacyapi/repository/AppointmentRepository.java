package com.example.mypharmacyapi.repository;

import com.example.mypharmacyapi.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
