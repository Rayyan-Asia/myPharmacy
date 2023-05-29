package com.example.mypharmacyapi.service.impl;

import com.example.mypharmacyapi.dto.DoctorDto;
import com.example.mypharmacyapi.dto.DoctorInsertDto;
import com.example.mypharmacyapi.dto.PersonDto;
import com.example.mypharmacyapi.entity.Doctor;
import com.example.mypharmacyapi.entity.Person;
import com.example.mypharmacyapi.repository.DoctorRepository;
import com.example.mypharmacyapi.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    @Override
    public DoctorDto insertDoctor(DoctorInsertDto doctorInsertDto) {
        Doctor doctor = modelMapper.map(doctorInsertDto, Doctor.class);
        Doctor newDoctor = doctorRepository.save(doctor);
        return modelMapper.map(newDoctor, DoctorDto.class);
    }

    @Override
    public DoctorDto getDoctor(long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isPresent()){
            return modelMapper.map(doctor.get(), DoctorDto.class);
        } else {
            return null;
        }
    }
}
