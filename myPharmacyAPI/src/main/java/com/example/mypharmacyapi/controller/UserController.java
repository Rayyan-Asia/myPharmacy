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
    ResponseEntity<UserDto> insertUser(@Valid @RequestBody UserDto user){
        return ResponseEntity.ok().body(userService.insertUser(user));
    }
    @PostMapping("/email")
    ResponseEntity<UserDto> getUser(@Valid @RequestBody UserDto user){
        UserDto userDto = userService.getUser(user.getEmail());
        if(userDto != null) {
            return ResponseEntity.ok().body(userDto);
        } else {
            return  ResponseEntity.badRequest().body(null);
        }
    }
}
