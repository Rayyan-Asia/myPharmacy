package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.AppointmentDto;
import com.example.mypharmacyapi.dto.AppointmentInsertDto;
import com.example.mypharmacyapi.dto.DoctorDto;
import com.example.mypharmacyapi.dto.DoctorInsertDto;
import com.example.mypharmacyapi.entity.Appointment;
import com.example.mypharmacyapi.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppointmentController {
    private final AppointmentService appointmentService;
    @PostMapping
    ResponseEntity<AppointmentDto> insertAppointment(@Valid @RequestBody AppointmentInsertDto appointment){
        return ResponseEntity.ok().body(appointmentService.insertAppointment(appointment));
    }
    @GetMapping("/{id}")
    ResponseEntity<AppointmentDto> getAppointment(@PathVariable long id){
        return ResponseEntity.ok().body(appointmentService.getAppointment(id));
    }

    @PostMapping("/all")
    ResponseEntity<List<AppointmentDto>> insertAppointments(@Valid @RequestBody List<AppointmentInsertDto> appointmentInsertDtos) {
        System.out.println("IM HITTTT");
        List<AppointmentDto> insertedAppointments = appointmentService.insertAppointments(appointmentInsertDtos);
        return ResponseEntity.ok().body(insertedAppointments);
    }
}
