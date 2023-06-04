package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.FamilyDto;
import com.example.mypharmacyapi.dto.FamilyInsertDto;
import com.example.mypharmacyapi.service.FamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FamilyController {
    private final FamilyService familyService;
    @PostMapping
    ResponseEntity<FamilyDto> insertFamily(@Valid @RequestBody FamilyInsertDto family){
        return ResponseEntity.ok().body(familyService.insertFamily(family));
    }
    @GetMapping("/{id}")
    ResponseEntity<FamilyDto> getFamily(@PathVariable long id){
        return ResponseEntity.ok().body(familyService.getFamily(id));
    }
}
