package com.example.mypharmacyapi.service.impl;

import com.example.mypharmacyapi.dto.*;
import com.example.mypharmacyapi.dto.AppointmentDto;
import com.example.mypharmacyapi.entity.Appointment;
import com.example.mypharmacyapi.entity.Doctor;
import com.example.mypharmacyapi.repository.AppointmentRepository;
import com.example.mypharmacyapi.repository.PersonRepository;
import com.example.mypharmacyapi.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    @Override
    public AppointmentDto insertAppointment(AppointmentInsertDto appointmentInsertDto) {
        Appointment appointment = modelMapper.map(appointmentInsertDto, Appointment.class);
        Appointment newAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(newAppointment, AppointmentDto.class);
    }

    @Override
    public AppointmentDto getAppointment(long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if(appointment.isPresent()){
            return modelMapper.map(appointment.get(), AppointmentDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<AppointmentDto> insertAppointments(List<AppointmentInsertDto> appointmentInsertDtos) {
        List<AppointmentDto> appointmentDtos = new ArrayList<>();
        for(AppointmentInsertDto appointmentInsertDto: appointmentInsertDtos) {
            Appointment appointment = modelMapper.map(appointmentInsertDto, Appointment.class);
            Appointment newAppointment = appointmentRepository.save(appointment);
            appointmentDtos.add(modelMapper.map(newAppointment, AppointmentDto.class));
        }
        return appointmentDtos;
    }
}
