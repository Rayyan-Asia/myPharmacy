package com.example.mypharmacyapi.dto;

import com.example.mypharmacyapi.entity.Doctor;
import com.example.mypharmacyapi.entity.Person;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private int id;
    private String title;
    private String symptoms;
    private String diagnosis;
    private LocalDate dateOfAppointment;
    private Doctor doctor;
    private Person person;
}
