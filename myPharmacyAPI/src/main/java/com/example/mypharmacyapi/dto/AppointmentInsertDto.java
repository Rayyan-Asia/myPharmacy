package com.example.mypharmacyapi.dto;

import com.example.mypharmacyapi.entity.Doctor;
import com.example.mypharmacyapi.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentInsertDto {
    private String title;
    private String symptoms;
    private String diagnosis;
    private LocalDate dateOfAppointment;
    private Doctor doctor;
    private Person person;
}
