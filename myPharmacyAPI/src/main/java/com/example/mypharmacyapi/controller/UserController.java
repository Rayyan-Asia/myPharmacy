package com.example.mypharmacyapi.controller;

import com.example.mypharmacyapi.dto.UserDto;
import com.example.mypharmacyapi.dto.UserInsertDto;
import com.example.mypharmacyapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    @PostMapping
    ResponseEntity<UserDto> insertUser(@Valid @RequestBody UserInsertDto user){
        return ResponseEntity.ok().body(userService.insertUser(user));
    }
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable String id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }
}
