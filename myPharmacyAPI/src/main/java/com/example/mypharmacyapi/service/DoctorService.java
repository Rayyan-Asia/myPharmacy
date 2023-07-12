package com.example.mypharmacyapi.service;

import com.example.mypharmacyapi.dto.DoctorDto;
import com.example.mypharmacyapi.dto.DoctorInsertDto;

import java.util.List;

public interface DoctorService {
    DoctorDto insertDoctor(DoctorInsertDto doctor);

    DoctorDto getDoctor(long id);
    
    List<DoctorDto> insertDoctors(List<DoctorInsertDto> doctors);
}
