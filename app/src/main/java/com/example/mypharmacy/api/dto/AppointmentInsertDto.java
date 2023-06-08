package com.example.mypharmacy.api.dto;

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
    private DoctorDto doctor;
    private PersonDto person;
}
