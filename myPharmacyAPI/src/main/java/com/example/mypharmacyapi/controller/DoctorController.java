package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.DoctorDto;
import com.example.mypharmacyapi.dto.DoctorInsertDto;
import com.example.mypharmacyapi.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorController {
    private final DoctorService doctorService;
    @PostMapping
    ResponseEntity<DoctorDto> insertDoctor(@Valid @RequestBody DoctorInsertDto doctor){
        return ResponseEntity.ok().body(doctorService.insertDoctor(doctor));
    }
    @GetMapping("/{id}")
    ResponseEntity<DoctorDto> getDoctor(@PathVariable long id){
        return ResponseEntity.ok().body(doctorService.getDoctor(id));
    }
}
